package Conexion_;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    private static final String REMITENTE = "nicolasbecerramuro@gmail.com"; // Cambia por tu correo
    private static final String CLAVE = "yspqnoofirxbjvwi"; // Cambia por tu clave de aplicación

    public static void enviarCorreoCambioNIP(String destinatario, String nombre) {
        System.out.println("Intentando enviar correo a: " + destinatario);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITENTE, CLAVE);
            }
        });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(REMITENTE));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject("Cambio de NIP realizado");

            String cuerpo = String.format(
                "Estimado/a %s:\n\n" +
                "Le informamos que su NIP ha sido cambiado exitosamente.\n\n" +
                "Si usted no realizó este cambio, por favor contacte a su banco inmediatamente.\n\n" +
                "Atentamente,\nBanco Nacional",
                nombre
            );

            mensaje.setText(cuerpo);
            Transport.send(mensaje);

            System.out.println("Correo enviado correctamente a: " + destinatario);

        } catch (MessagingException e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
