package br.com.deveficiente.bolaoapi.services.poll;

import br.com.deveficiente.bolaoapi.shared.config.SpringContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.persistence.PostPersist;

public class PollListener {

    @PostPersist
    public void sendEmail(Poll poll) {
//        JavaMailSender mailSender = SpringContext.getBean(JavaMailSender.class);
        final String fromEmail = poll.getOwner().getLogin();
        poll.getInvitations().stream()
                .forEach(invitation -> {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(invitation.getEmail());
                    message.setFrom(fromEmail);
                    message.setText(this.buildInvitationLink(invitation));

                    System.out.println(this.buildInvitationLink(invitation));
//                    mailSender.send(message);
                });
    }

    private String buildInvitationLink(Invitation invitation) {
        String link = new StringBuilder()
                .append("http://localhost:8080")
                .append("/invitations?key=")
                .append(invitation.getKey())
                .toString();

        return link;
    }
}
