package io.tacsio.mercadolivre.service.mail;

public interface Notifiable {

    String subject();

    String body();

    String to();

    String from();

}
