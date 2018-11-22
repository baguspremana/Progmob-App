package com.example.user_pc.semnas_ti.bantuan;

public class ConstantURL {
    public static final String BASE_URL = "http://10.10.22.139:8000/";

    public static final class Role{
        public static final int USER=1;
        public static final int ADMIN=2;
    }

    public static class URL{
        public static String api(){
            return BASE_URL+"api/";
        }
        public static String qrCode(String file){
            return BASE_URL+"uploads/qrcode/"+file;
        }
        public static String imgPhoto(String file){
            return BASE_URL+"uploads/verification/"+file;
        }
    }
}
