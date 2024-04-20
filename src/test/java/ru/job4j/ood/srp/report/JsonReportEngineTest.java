package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

class JsonReportEngineTest {

    @Test
    public void whenOldGenerated() {
        MemoryStore store = new MemoryStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 6500);
        Employee worker2 = new Employee("Viktor", now, now, 7000);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        store.add(worker);
        store.add(worker2);
        JsonReportEngine engine = new JsonReportEngine(store, parser);
        StringBuilder expected = new StringBuilder()
            .append("[")
            .append("{\"name\":\"")
            .append(worker.getName())
            .append("\",\"hired\":\"")
            .append(parser.parse(worker.getHired()))
            .append("\",\"fired\":\"")
            .append(parser.parse(worker.getFired()))
            .append("\",\"salary\":")
            .append(worker.getSalary())
            .append("}")
            .append(",")
            .append("{\"name\":\"")
            .append(worker2.getName())
            .append("\",\"hired\":\"")
            .append(parser.parse(worker2.getHired()))
            .append("\",\"fired\":\"")
            .append(parser.parse(worker2.getFired()))
            .append("\",\"salary\":")
            .append(worker2.getSalary())
            .append("}")
            .append("]");
        assertThat(engine.generate(employee -> true)).isEqualTo(expected.toString());
    }

}