package com.jumpitt.primero_cotiza.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDifference {

    public DateDifference() {
    }

    public String getDaysBetween(String dateStart) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = d2.getTime() - d1.getTime();
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffHours<1){
            return "Recientemente";
        }else if (diffHours <24){
            return "Hace "+ diffHours+ " horas";
        } else if (diffDays==1){
            return "Ayer";
        } else if (diffDays>1 && diffDays<31) {
            return "Hace " + diffDays + " días";
        } else if (diffDays>31 && diffDays<365){
            return "Hace " + diffDays/30 + " meses";
        } else {
            return "Hace más de un año";
        }
    }
}
