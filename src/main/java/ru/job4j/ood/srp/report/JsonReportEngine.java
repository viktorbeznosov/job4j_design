package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;
import java.util.function.Predicate;

public class JsonReportEngine extends ReportEngine {

    protected final Gson library;

    public JsonReportEngine(Store store, DateTimeParser<Calendar> dateTimeParser) {
        super(store, dateTimeParser);
        library = new GsonBuilder().create();
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        JsonArray employees = new JsonArray();
        for (Employee employee : store.findBy(filter)) {
            JsonObject object = new JsonObject();
            object.addProperty("name", employee.getName());
            object.addProperty("hired", dateTimeParser.parse(employee.getHired()));
            object.addProperty("fired", dateTimeParser.parse(employee.getFired()));
            object.addProperty("salary", employee.getSalary());
            employees.add(object);
        }

        return library.toJson(employees);
    }

    public static void main(String[] args) {
        MemoryStore store = new MemoryStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 6500);
        Employee worker2 = new Employee("Viktor", now, now, 7000);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        store.add(worker);
        store.add(worker2);
        Report engine = new JsonReportEngine(store, parser);
        System.out.println(engine.generate(s -> true));
    }
}
