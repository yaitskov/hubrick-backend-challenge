package org.dan.csvql;

import static java.util.Arrays.asList;
import static org.dan.csvql.Column.ofDouble;
import static org.dan.csvql.Column.ofInt;
import static org.dan.csvql.Column.ofString;

import java.nio.file.Path;

import javax.swing.plaf.synth.SynthLookAndFeel;

public class Reports {
    private static final Column EMPLOYEE_NAME = ofString("employee-name");
    private static final EqualRelationalPredicateFactory BY_EMPLOYEE_NAME = new EqualRelationalPredicateFactory(EMPLOYEE_NAME.getName());
    private static final Column DEPARTMENT_ID = ofInt("department-id");
    private static final EqualRelationalPredicateFactory BY_DEPARTMENT_ID = new EqualRelationalPredicateFactory(DEPARTMENT_ID.getName());
    private static final Column DEPARTMENT_NAME = ofString("department-name");
    private static final Column SALARY = ofDouble("salary");

    private final AppParams params;

    public Reports(AppParams params) {
        this.params = params;
    }

    public void build() {
        final RelationalSet allRelation = loadAllAndJoin();
        incomeByDepartment(allRelation);
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
                EMPLOYEE_NAME, ofInt("employee-age"));

        final RelationalSet employeeRelation = readFile("employees.csv",
                DEPARTMENT_ID, EMPLOYEE_NAME, ofString("employee-gender"),
                SALARY);

        return new BrutalInnerJoinOperator(BY_EMPLOYEE_NAME)
                .apply(
                        ageRelation,
                        new BrutalInnerJoinOperator(BY_DEPARTMENT_ID)
                                .apply(employeeRelation, departmentRelation));
    }

    private void incomeByDepartment(RelationalSet allRelation) {
        final RelationalSet result = new Grouper(DEPARTMENT_NAME.getName(),
                SALARY.getName(), new Median(0.5))
                .group(allRelation);
        writeFile("income-by-department.csv",
                new Sorter(SALARY).sort(result));
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
