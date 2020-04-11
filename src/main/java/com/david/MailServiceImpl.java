package com.david;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("mailService")
public class MailServiceImpl implements MailService {
 
    @Autowired
    JavaMailSender mailSender;
 
    public void sendEmail(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);


        try {

            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
 
            mailSender.send(mimeMessageHelper.getMimeMessage());
 
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (MailException me) {
            if (me instanceof MailSendException) {
                Exception[] messageExceptions = ((MailSendException) me).getMessageExceptions();
                Exception ex1 = messageExceptions[0];
                SendFailedException sfe = (SendFailedException) ex1;
                Address[] validUnsent = sfe.getValidUnsentAddresses();
                Address[] invalidAddreses = sfe.getInvalidAddresses();
                List<Address> unsent = Arrays.asList(validUnsent);
                unsent.addAll(Arrays.asList(invalidAddreses));
                List<String> nonSentAddresses = unsent.stream().map(Address::toString)
                        .collect(Collectors.toList());
                System.out.println(String.format("The mail could not be delivered to the following addresses: %s", nonSentAddresses.toString()));
            }
            System.out.println();
        }
    }
 
}