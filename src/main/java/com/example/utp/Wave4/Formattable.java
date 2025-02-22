package com.example.utp.Wave4;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public interface Formattable {
    public default double convertNumber(String numstr, Locale loc) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(loc);
        try {
            return df.parse(numstr).doubleValue();
        } catch(ParseException pe) {
            throw new IllegalArgumentException("Could not parse " + numstr);
        }
    }

    public default String convertAmount(String textNum, Locale from, Locale to) {
        DecimalFormatSymbols fromSymbols = new DecimalFormatSymbols(from);
        DecimalFormatSymbols toSymbols = new DecimalFormatSymbols(to);

        DecimalFormat original = (DecimalFormat) DecimalFormat.getInstance(from);
        DecimalFormat conversion = (DecimalFormat) DecimalFormat.getInstance(to);


        original.setDecimalFormatSymbols(fromSymbols);
        conversion.setDecimalFormatSymbols(toSymbols);

        try {
            Number parsed = original.parse(textNum);
            return conversion.format(parsed);

        } catch(ParseException pe) {
            pe.printStackTrace();
            return textNum;
        }
    }

    public default String convertDate(String datestring, SimpleDateFormat from, SimpleDateFormat to) {
        try {
            Date sd = from.parse(datestring);
            return to.format(sd);
        } catch(ParseException pe) {
            pe.printStackTrace();
            return datestring;
        }
    }
}