package com.example.user_pc.semnas_ti.bantuan;

public class ConstantURL {
    public static final String BASE_URL = "http://10.164.248.211:8000/";

    public static final class Role{
        public static final int USER=1;
        public static final int ADMIN=2;
    }

    public static class URL{
        public static String api(){
            return BASE_URL+"api/";
        }
    }
}
