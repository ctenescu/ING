package com.lucian.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[a-zA-z0-9!#$%&'*+-\\/=?^_`{|}~]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$", Pattern.CASE_INSENSITIVE);

    public static Boolean checkEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
