package br.com.deveficiente.bolaoapi.services.poll.api.service;

import br.com.deveficiente.bolaoapi.services.poll.Invitation;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InvitationEmailSender {

    //    private JavaMailSender mailSender;

    public void sendInvitationsByEmail(Set<Invitation> invitations) {
        invitations.forEach(invitation -> {
            String ownerLogin = invitation.getPoll().getOwner().getLogin();
            System.out.println(invitation.getInvitationLink() + "&accept=true");
            System.out.println(invitation.getInvitationLink() + "&accept=false");
//            mailSender.send(buildMessage(ownerLogin, invitation));
        });
    }

    private MailMessage buildMessage(String from, Invitation invitation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(invitation.getEmail());
        message.setFrom(from);
        message.setText(invitation.getInvitationLink());

        return message;
    }

}
