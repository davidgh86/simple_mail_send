package com.david;

import javax.mail.MessagingException;

public interface MailService {
    public void sendEmail(Mail mail) throws MessagingException;
}