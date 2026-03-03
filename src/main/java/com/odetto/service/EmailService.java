package com.odetto.service;

import com.odetto.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromAddress;

    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.from:nao-responda@odetto.com}") String fromAddress) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
    }

    @Async
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
                "Prezado(a) aluno(a),\n\n" +
                        "Seja bem-vindo(a) ao Odetto! Seu pré-cadastro foi realizado com sucesso.\n\n" +
                        "Para acessar a plataforma, utilize as credenciais abaixo:\n" +
                        "--------------------------------------\n" +
                        "USUÁRIO (CPF): %d\n" +
                        "SENHA (MATRÍCULA): %d\n" +
                        "--------------------------------------\n\n" +
                        "Este é o seu primeiro acesso. Após o login, você deverá:\n" +
                        "1. Preencher seu nome completo.\n" +
                        "2. Alterar sua senha para uma de sua preferência.\n\n" +
                        "Atenciosamente,\n" +
                        "Administração Odetto",
                student.getCpf(), student.getEnrollment()
        );
    }
}