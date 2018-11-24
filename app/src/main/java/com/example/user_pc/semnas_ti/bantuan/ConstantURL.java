package com.example.user_pc.semnas_ti.bantuan;

public class ConstantURL {
    public static final String BASE_URL = "http://192.168.42.86:8000/";

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

        public static String imgUser(String file){
            return BASE_URL+"uploads/profile/"+file;
        }
    }
}
