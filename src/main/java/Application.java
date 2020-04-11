import com.david.Mail;
import com.david.MailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.mail.MessagingException;
import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.david"
})
public class Application {
    public static void main(String[] args) throws MessagingException {
 
        Mail mail = new Mail();
        mail.setMailFrom("gabriel@test.com");
        mail.setMailTo(new String[]{"david@test.com"});
        mail.setMailBcc(new String[]{"davidad@dfasdfesafsafds.com","gabriel@test.com","david@test.com"});
        mail.setMailSubject("Spring Boot - Email Example");
        mail.setMailContent("Learn How to send Email using Spring Boot!!!");
 
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        MailService mailService = (MailService) ctx.getBean("mailService");
        mailService.sendEmail(mail);
 
    }
}