package br.com.douglas.flat.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by douglas on 20/01/15.
 */
public class DateHelper {

    private static String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static String dateBrFormat = "dd/MM/yyyy";

    public static String getString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                DateHelper.dateFormat, Locale.getDefault());

        return dateFormat.format(date);
    }

    public static Date getDate(String string) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DateHelper.dateFormat);

        return (Date) format.parse(string);
    }

    public static Date getDateBr(String string) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DateHelper.dateBrFormat);

        return (Date) format.parse(string);
    }

    public static String getStringBr(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                dateBrFormat, Locale.getDefault());

        return dateFormat.format(date);
    }
}
