package org.dan.csvql;

import static java.util.Arrays.asList;
import static org.dan.csvql.Column.ofDouble;
import static org.dan.csvql.Column.ofInt;
import static org.dan.csvql.Column.ofString;

import java.nio.file.Path;

public class Reports {
    private static final Column EMPLOYEE_NAME = ofString("employee-name");
    private static final EqualRelationalPredicateFactory BY_EMPLOYEE_NAME = new EqualRelationalPredicateFactory(EMPLOYEE_NAME.getName());
    private static final Column DEPARTMENT_ID = ofInt("department-id");
    private static final EqualRelationalPredicateFactory BY_DEPARTMENT_ID = new EqualRelationalPredicateFactory(DEPARTMENT_ID.getName());
    private static final Column DEPARTMENT_NAME = ofString("department-name");
    private static final Column SALARY = ofDouble("salary");
    private static final Column EMPLOYEE_AGE = ofInt("employee-age");
    private static final Column EMPLOYEE_AVERAGE_AGE = ofInt("employee-average-age");
    private static final int YEARS_RANGE = 10;

    private final AppParams params;

    public Reports(AppParams params) {
        this.params = params;
    }

    public void build() {
        final RelationalSet allRelation = loadAllAndJoin();
        incomeByDepartment(allRelation, "income-by-department.csv", 0.5);
        incomeByDepartment(allRelation, "income-95-by-department.csv", 0.95);
        averageIncomeByAgeRange(allRelation, "income-average-by-age-range.csv");
        employeeAgeByDepartment(allRelation, "employee-age-by-department.csv");
    }

    private void employeeAgeByDepartment(RelationalSet allRelation, String name) {
        final RelationalSet result = new Grouper(DEPARTMENT_NAME.getName(),
                EMPLOYEE_AGE.getName(), new Median(0.5))
                .group(allRelation);
        writeFile(name,
                new Sorter(DEPARTMENT_NAME).sort(result));
    }

    private Path resolve(String other) {
        return params.getDataFolder().resolve(other);
    }

    private RelationalSet loadAllAndJoin() {
        final RelationalSet departmentRelation =
                new BrutalInnerJoinOperator(new TrueRelationalPredicateFactory())
                        .apply(
                                new Sorter(DEPARTMENT_NAME)
                                        .sort(readFile("departments.csv",
                                                DEPARTMENT_NAME)),
                                new EnumeratingSet(DEPARTMENT_ID.getName())) ;

        final RelationalSet ageRelation = readFile("ages.csv",
                EMPLOYEE_NAME, EMPLOYEE_AGE);

        final RelationalSet employeeRelation = readFile("employees.csv",
                DEPARTMENT_ID, EMPLOYEE_NAME, ofString("employee-gender"),
                SALARY);

        return new BrutalInnerJoinOperator(BY_EMPLOYEE_NAME)
                .apply(
                        ageRelation,
                        new BrutalInnerJoinOperator(BY_DEPARTMENT_ID)
                                .apply(employeeRelation, departmentRelation));
    }

    private void incomeByDepartment(RelationalSet allRelation, String name, double ration) {
        final RelationalSet result = new Grouper(DEPARTMENT_NAME.getName(),
                SALARY.getName(), new Median(ration))
                .group(allRelation);
        writeFile(name,
                new Sorter(SALARY).sort(result));
    }

    private void averageIncomeByAgeRange(RelationalSet allRelation, String name) {
        final RelationalSet roundedAge = new Synthesizer()
                .synthesize(allRelation, EMPLOYEE_AGE.getName(),
                        EMPLOYEE_AVERAGE_AGE.getName(),
                        new ClusterizeFunc(YEARS_RANGE));

        final RelationalSet result = new Grouper(
                EMPLOYEE_AVERAGE_AGE.getName(),
                SALARY.getName(), new Average())
                .group(roundedAge);
        writeFile(name,
                new Sorter(EMPLOYEE_AVERAGE_AGE).sort(result));
    }

    private RelationalSet readFile(String name, Column... columns) {
        return MoreFiles.readFrom(resolve(name),
                params.getInputCharset(),
                new CsvLoader(asList(columns))::load);
    }

    private void writeFile(String name, RelationalSet set) {
        MoreFiles.writeTo(resolve(name),
                params.getOutputCharset(),
                writer -> new CsvWriter(writer::print)
                        .write(set));
    }
}
