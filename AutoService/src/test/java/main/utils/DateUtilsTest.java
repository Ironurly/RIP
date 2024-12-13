package main.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtilsTest {
    @Test
    public void testParseDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(2020, 04, 20, 13, 30, 00);

        Long expectedMillis = calendar.getTimeInMillis();
        Long parsedMillis = DateUtils.parse("2020-05-20 13:30").getTime();

        // С точностью до секунды
        Assertions.assertTrue(Math.abs(expectedMillis - parsedMillis) <= 1000);

        // С буквой Т, как с фронта
        parsedMillis = DateUtils.parse("2020-05-20T13:30").getTime();
        Assertions.assertTrue(Math.abs(expectedMillis - parsedMillis) <= 1000);
    }

    @Test
    public void testToStringDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(2020, 04, 20, 13, 30, 00);

        Assertions.assertEquals("2020-05-20T13:30", DateUtils.toString(calendar.getTime()));
    }
}
