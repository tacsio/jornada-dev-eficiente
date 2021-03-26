package io.tacsio.mercadolivre.service.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    //fake mail
    public void sendMail(Notifiable notification) {
        log.info("subject: " + notification.subject());
        log.info("body: " + notification.body());
        log.info("to: " + notification.to());
        log.info("from: " + notification.from());
    }
}
