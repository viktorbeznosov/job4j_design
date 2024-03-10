package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

class HRReportEngineTest {
    @Test
    public void whenOldGenerated() {
        MemoryStore store = new MemoryStore();
        Calendar now = Calendar.getInstance();
        Employee ivan = new Employee("Ivan", now, now, 6500);
        Employee sergey = new Employee("Sergey", now, now, 8000);
        Employee tatiana = new Employee("Tatiana", now, now, 5000);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        store.add(ivan);
        store.add(sergey);
        store.add(tatiana);
        Report engine = new HRReportEngine(store, parser);
        StringBuilder expected = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())
                .append(sergey.getName()).append(" ")
                .append(parser.parse(sergey.getHired())).append(" ")
                .append(parser.parse(sergey.getFired())).append(" ")
                .append(sergey.getSalary())
                .append(System.lineSeparator())
                .append(ivan.getName()).append(" ")
                .append(parser.parse(ivan.getHired())).append(" ")
                .append(parser.parse(ivan.getFired())).append(" ")
                .append(ivan.getSalary())
                .append(System.lineSeparator())
                .append(tatiana.getName()).append(" ")
                .append(parser.parse(tatiana.getHired())).append(" ")
                .append(parser.parse(tatiana.getFired())).append(" ")
                .append(tatiana.getSalary())
                .append(System.lineSeparator());
        assertThat(engine.generate(employee -> true)).isEqualTo(expected.toString());
    }
}