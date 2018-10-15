package si.um.feri.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/*
 * Created by David on 31.5.2017.
 *
 *
 * */
    public class MailSender {

        private Session seja;
        private Message sporocilo;
        private InternetAddress posiljatelj;
        private Properties props;

        private static MailSender instance = null;

        public void posljiEmail(String prejemnik1, String naslov, String vsebina) {

            props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            seja = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("info.ekslovenija@gmail.com", "kmetija123");
                }

            });
            try {
                sporocilo = new MimeMessage(seja);

                posiljatelj = new InternetAddress("info.ekslovenija@gmail.com");

                sporocilo.setFrom(posiljatelj);
                sporocilo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(prejemnik1));
                sporocilo.setSubject(naslov);
                sporocilo.setContent(vsebina,"text/html; charset=UTF-8");

                Transport.send(sporocilo);

            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        }
        	public static synchronized MailSender getInstance() {
		if (instance == null) {
			instance = new MailSender();
		}
		return instance;
	}
}
