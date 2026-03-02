package com.odetto.service;

import com.odetto.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromAddress;

    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.from:nao-responda@odetto.com}") String fromAddress) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
    }

    public void sendPreCadastroEmail(Student student) {
        String subject = "Pré-cadastro realizado — Odetto";
        String text = buildPreCadastroText(student);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAddress);
        msg.setTo(student.getEmail());
        msg.setSubject(subject);
        msg.setText(text);

        mailSender.send(msg);
    }

    private String buildPreCadastroText(Student student) {
        return String.format(
                "Olá,\n\n" +
                        "Você foi pré-cadastrado no sistema Odetto.\n\n" +
                        "Matrícula: %d\n" +
                        "Senha inicial: %d\n\n" +
                        "Use seu CPF como usuário e a matrícula acima como senha para fazer o primeiro login.\n" +
                        "No primeiro acesso, complete seu cadastro (coloque seu nome) e altere a senha.\n\n" +
                        "Se você não solicitou esse pré-cadastro, entre em contato com a secretaria.\n\n" +
                        "Atenciosamente,\nEquipe Odetto",
                student.getEnrollment(), student.getEnrollment()
        );
    }
}