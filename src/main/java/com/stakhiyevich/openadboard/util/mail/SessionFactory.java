package com.stakhiyevich.openadboard.util.mail;

import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import java.util.Properties;

class SessionFactory {

    static final String USER_NAME = "mail.smtp.user";
    static final String USER_PASSWORD = "mail.smtp.password";

    private SessionFactory() {
    }

    static Session createSession(Properties configProperties) {
        String userName = configProperties.getProperty(USER_NAME);
        String userPassword = configProperties.getProperty(USER_PASSWORD);
        return Session.getInstance(configProperties,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, userPassword);
                    }
                });
    }
}
