package co.edu.unicauca.oci.backend.apirest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements IMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(String to, String body) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom("albeiro9712@gmail.com");
        mail.setTo(to);
        mail.setSubject("Credenciales para ingresar al sistema OCI");
        mail.setText(body);

        javaMailSender.send(mail);

    }

}