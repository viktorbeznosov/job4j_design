package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.formatter.DateAdapter;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;
import ru.job4j.ood.srp.store.Store;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

public class XmlReportEngine extends ReportEngine {

    protected final JAXBContext context;

    private final Marshaller marshaller;

    public XmlReportEngine(Store store, DateTimeParser<Calendar> dateTimeParser) throws JAXBException {
        super(store, dateTimeParser);
        context = JAXBContext.newInstance(Employees.class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }

    @XmlRootElement(name = "employees")
    private static class Employees {
        private List<Employee> employees;

        public Employees() {

        }

        public Employees(List<Employee> employees) {
            this.employees = employees;
        }

        @XmlElement(name = "employee")
        public List<Employee> getEmployees() {
            return employees;
        }

        public void setEmployees(List<Employee> employees) {
            this.employees = employees;
        }
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        String xml = "";
        Employees employees = new Employees(store.findBy(filter));
        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(employees, writer);
            xml = writer.getBuffer().toString();
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public static void main(String[] args) throws JAXBException {
        MemoryStore store = new MemoryStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 6500);
        System.out.println(worker.getHired());
        Employee worker2 = new Employee("Viktor", now, now, 7000);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        store.add(worker);
        store.add(worker2);
        Report engine = new XmlReportEngine(store, parser);
        System.out.println(engine.generate(s -> true));
    }
}
