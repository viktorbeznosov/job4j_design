package ru.job4j.ood.srp.formatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateAdapter extends XmlAdapter<String, Calendar> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd:MM:yyyy HH:mm");

    @Override
    public String marshal(Calendar d) {
        return DATE_FORMAT.format(d.getTime());
    }

    @Override
    public Calendar unmarshal(String d) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DATE_FORMAT.parse(d));
        return cal;
    }
}
