package com.stakhiyevich.openadboard.util.validator;

public class ValidatorPatternHolder {

    //1-30, letters, digits, whitespaces, :.'- symbols
    public static final String NAME_PATTERN = "^([\\w\\s:.'-]{1,30})$";
    //valid email such as name@gmail.com
    public static final String EMAIL_PATTERN = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$";
    //6-50, at least one digit one lower letter and one capital letter
    public static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=\\S+$).{6,50}$";
    //1-50, letters, digits, whitespaces, :.,-' symbols
    public static final String TITLE_PATTERN = "^([\\w\\s:.,'-]{1,50})$";

    private ValidatorPatternHolder() {
    }
}
