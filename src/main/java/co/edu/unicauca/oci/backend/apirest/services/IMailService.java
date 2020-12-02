package co.edu.unicauca.oci.backend.apirest.services;

public interface IMailService {

    public void sendMail(String to, String body);
}