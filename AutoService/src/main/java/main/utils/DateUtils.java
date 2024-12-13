package main.utils;

import io.micrometer.common.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateUtils {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * Конвертирует строку формата {@code yyyy-MM-dd HH:mm} в объект типа {@code Date}.
     * @param s дата в виде строки
     * @return объект типа {@code Date}
     */
    public static Date parse(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        if (s.contains("T")) {
            s = s.replace('T', ' ');
        }
        try {
            return formatter.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Конвертирует объект типа {@code Date} в строку формата {@code yyyy-MM-dd}.
     *
     * @param date объект типа {@code Date}
     * @return дата в виде строки
     */
    public static String toString(Date date) {
        if (Objects.isNull(date)) {
            return "";
        }
        return formatter.format(date).replace(' ', 'T');
    }
}
