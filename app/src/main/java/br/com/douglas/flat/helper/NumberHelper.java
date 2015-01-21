package br.com.douglas.flat.helper;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Currency;

/**
 * Created by douglas on 21/01/15.
 */
public class NumberHelper {
    private static Currency currency = Currency.getInstance("BRL");
    private static DecimalFormat formato = new DecimalFormat("#,##0.00");

    public static String parseString(double value){
        return formato.format(value);
    }

    public static Double parseDouble(String value) throws ParseException, ClassCastException {
        value = value.replace("R$","");
        value = value.replace(".","");
        value = value.replace(",",".");

        return Double.parseDouble(value);
    }
}
