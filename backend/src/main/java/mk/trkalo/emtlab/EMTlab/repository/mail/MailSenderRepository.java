package mk.trkalo.emtlab.EMTlab.repository.mail;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

public interface MailSenderRepository {
    void sendHtmlMail(String to,
                      String subject,
                      String template,
                      Map<String, String> params) throws MessagingException, IOException;
}
