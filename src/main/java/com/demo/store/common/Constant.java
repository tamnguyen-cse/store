package com.demo.store.common;

public class Constant {

    private Constant() {
    }

    public static final String ENCODING = "UTF-8";
    public static final String BASE_CONFIG_PATH = "classpath:config/";


    public static final class Params {

        public static final String ID = "id";
        public static final String VALUE = "value";
        public static final String STATUS = "status";
        public static final String USER_ID = "userId";

        private Params() {
        }

    }


    public static final class Module {
        public static final String USER_MODULE = "user";

        private Module() {
        }
    }


    public static class Symbol {
        public static final String DOT = ".";
        public static final String AND = "&";
        public static final String SPACE = " ";
        public static final String COLON = ":";
        public static final String COMMA = ",";
        public static final String SLASH = "/";
        public static final String HYPHEN = "-";
        public static final String PERCENT = "%";
        public static final String AT_SIGN = "@";
        public static final String ASTERISK = "*";
        public static final String SEMICOLON = ";";
        public static final String QUESTION_MARK = "?";

        private Symbol() {
        }
    }

}
