package com.notificacaoservice.rabbitmq;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender javaMail;

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);


    public CompletableFuture<Void> sendEmail(String to, String subject, String htmlContent) {
        return CompletableFuture.runAsync(() -> {
            try {
                log.info("Enviando email");

                MimeMessage message = javaMail.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setFrom("Ponto registrado <erickdecker23@gmail.com>");
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(htmlContent, true);

                javaMail.send(message);
                log.info("E-mail enviado com sucesso para: {}", to);

            } catch (MessagingException e) {
                System.out.println("Erro ao enviar e-mail HTML: " + e.getMessage());
            }
        });
    }




}