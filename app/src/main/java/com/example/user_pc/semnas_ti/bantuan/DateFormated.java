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
}
