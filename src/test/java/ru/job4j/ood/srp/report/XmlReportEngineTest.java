package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;

import javax.xml.bind.JAXBException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

class XmlReportEngineTest {

    @Test
    public void whenOldGenerated() throws JAXBException {
        MemoryStore store = new MemoryStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 6500);
        Employee worker2 = new Employee("Viktor", now, now, 7000);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        store.add(worker);
        store.add(worker2);
        XmlReportEngine engine = new XmlReportEngine(store, parser);
        StringBuilder expected = new StringBuilder()
            .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
            .append(System.lineSeparator())
            .append("<employees>")
            .append(System.lineSeparator()).append("    ")
            .append("<employees>")
            .append(System.lineSeparator()).append("        ")
            .append("<fired>" + dateFormat.format(worker.getFired().getTime()) + "</fired>")
            .append(System.lineSeparator()).append("        ")
            .append("<hired>" + dateFormat.format(worker.getHired().getTime()) + "</hired>")
            .append(System.lineSeparator()).append("        ")
            .append("<name>" + worker.getName() + "</name>")
            .append(System.lineSeparator()).append("        ")
            .append("<salary>" + worker.getSalary() + "</salary>")
            .append(System.lineSeparator()).append("    ")
            .append("</employees>")
            .append(System.lineSeparator()).append("    ")
            .append("<employees>")
            .append(System.lineSeparator()).append("        ")
            .append("<fired>" + dateFormat.format(worker2.getFired().getTime()) + "</fired>")
            .append(System.lineSeparator()).append("        ")
            .append("<hired>" + dateFormat.format(worker2.getHired().getTime()) + "</hired>")
            .append(System.lineSeparator()).append("        ")
            .append("<name>" + worker2.getName() + "</name>")
            .append(System.lineSeparator()).append("        ")
            .append("<salary>" + worker2.getSalary() + "</salary>")
            .append(System.lineSeparator()).append("    ")
            .append("</employees>")
            .append(System.lineSeparator())
            .append("</employees>")
            .append(System.lineSeparator());
        assertThat(engine.generate(employee -> true)).isEqualTo(expected.toString());
    }

}