package Utileria;

import Logs.AppLogs;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailSender {

    private static final AppLogs appLogs = new AppLogs(EmailSender.class);

    public static void enviarCertificado(String destinatario, String rutaArchivo) {
        final String remitente = "tucorreo@tudominio.com";
        final String password = "tu_contraseña";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.tudominio.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(remitente, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Certificado de curso");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Adjunto se encuentra su certificado.");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Adjuntar archivo PDF
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(rutaArchivo);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(source.getName());
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            appLogs.infoLogs("Correo enviado correctamente a " + destinatario);

        } catch (MessagingException e) {
            appLogs.errorLogs(e);
        }
    }
}
