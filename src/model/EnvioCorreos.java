package model;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import view.RecuperacionContrasenaInterfaz;

public class EnvioCorreos {

    private final RecuperacionContrasenaInterfaz ri;

    private static final String EMAIL_FROM = "jeancarlosburbanopantoja5678@gmail.com";
    private static final String CONTRASENA_FROM = "ujjq nmep lygy docd";
    private static final String ASUNTO_TO = "LifeDoc-Care | Codigo de verificacion para recuperar su contrasena";
    private static final String CONTENIDO_TO = "Estimado usuario,\n\n"
            + "Hemos recibido una solicitud para restablecer la contrasena asociada a su cuenta "
            + "en LifeDoc-Care. Para continuar con el proceso, utilice el siguiente codigo de verificacion:\n\n"
            + "Codigo de verificacion: ";
    private static final String CONTENIDO_PIE = "\n\nEste codigo es personal e intransferible; "
            + "por su seguridad, no lo comparta con nadie, ni siquiera con personal de LifeDoc-Care.\n"
            + "Si usted no solicito este cambio, ignore este mensaje y su contrasena actual seguira siendo valida.\n\n"
            + "Atentamente,\nEquipo LifeDoc-Care  La bestia Alejandro Vanegas Tu papa";

    private String emailTo;
    private Properties properties;
    private Session session;
    private MimeMessage mimeMessage;
    private Integer numeroAzar;

    public EnvioCorreos(RecuperacionContrasenaInterfaz ri) {
        this.ri = ri;
        this.properties = new Properties();
    }

    private void createEmail() {
        this.emailTo = this.ri.field.getText().trim();

        Random random = new Random();
        this.numeroAzar = random.nextInt(9000) + 1000; // 1000 - 9999
        String contenidoFinal = CONTENIDO_TO + numeroAzar + CONTENIDO_PIE;

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Se usa un Authenticator para que la sesion quede autenticada de una vez;
        // asi Transport.send(mimeMessage) ya sabe con que credenciales conectarse.
        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, CONTRASENA_FROM);
            }
        });

        try {
            mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(EMAIL_FROM));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mimeMessage.setSubject(ASUNTO_TO);
            mimeMessage.setText(contenidoFinal, "UTF-8");
        } catch (AddressException e) {
            Logger.getLogger(EnvioCorreos.class.getName()).log(Level.SEVERE, null, e);
            mimeMessage = null;
        } catch (MessagingException e) {
            Logger.getLogger(EnvioCorreos.class.getName()).log(Level.SEVERE, null, e);
            mimeMessage = null;
        }
    }

    private boolean sentEmail() {
        try {
            Transport.send(mimeMessage);
            System.out.println("Correo enviado a " + emailTo);
            return true;
        } catch (MessagingException e) {
            Logger.getLogger(EnvioCorreos.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean enviarCorreoRecuperacion() {
        createEmail();
        if (mimeMessage == null) {
            return false;
        }
        return sentEmail();
    }

    public Integer getNumeroAzar() {
        return numeroAzar;
    }

    public void eliminarValorNumeroAzar() {
        numeroAzar = null;
    }
}