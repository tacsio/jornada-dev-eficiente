package br.com.deveficiente.bolaoapi.services.poll.api.service;

import br.com.deveficiente.bolaoapi.services.poll.Invitation;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Set;

@Service
public class InvitationEmailSender {

    //    private JavaMailSender mailSender;

    public void sendInvitationsByEmail(Set<Invitation> invitations) {
        String host = ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString();
        invitations.forEach(invitation -> {
            String ownerLogin = invitation.getPoll().getOwner().getLogin();
            System.out.println(invitation.getAcceptLink(host));
            System.out.println(invitation.getDenyLink(host));
//            mailSender.send(buildMessage(host, ownerLogin, invitation));
        });
    }

    private MailMessage buildMessage(String host, String from, Invitation invitation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(invitation.getEmail());
        message.setFrom(from);

        String msg = """
                Accept Link: %s 
                Deny Link: %s
                """;

        message.setText(msg.formatted(
                invitation.getAcceptLink(host),
                invitation.getDenyLink(host))
        );

        return message;
    }

}
