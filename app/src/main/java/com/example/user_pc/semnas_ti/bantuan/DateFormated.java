package com.example.user_pc.semnas_ti.bantuan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormated {

    public static String formatDate(String tanggal){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(tanggal);
            SimpleDateFormat simpleDateFormatOut = new SimpleDateFormat("dd-MMM-yyyy");
            return simpleDateFormatOut.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String dateValidation(int value){
        if (value<10){
            return "0"+value;
        }
        return String.valueOf(value);
    }

    public  static String getMonthName(String month){
        Date date= null;
        try {
            date = new SimpleDateFormat("MM").parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("MMMM").format(date);
    }

    public  static String setDate(String oldDate){
        Date date= null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("dd MMMM yyyy HH:mm").format(date);
    }

    public static String setTanggal(String oldDate){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("dd").format(date);
    }

    public static String setBulan(String oldDate){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("MMMM").format(date);
    }

    public static String setTahun(String oldDate){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("yyyy").format(date);
    }

    public static String setWaktu(String oldDate){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("HH:mm").format(date);
    }
}
