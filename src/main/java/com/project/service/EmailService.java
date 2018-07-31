package com.project.service;

import com.project.config.ApplicationProperties;
import com.project.dtos.ClientGetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService extends BaseService {

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void send(ClientGetDto clientGetDto) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());


        Context context = new Context();
        context.setVariable("address", clientGetDto.getAddress());
        context.setVariable("fullName", clientGetDto.getFirstName()+"  "+clientGetDto.getLastName());
        context.setVariable("location",  clientGetDto.getLocation());
        context.setVariable("signature",  "https://memorynotfound.com");
        context.setVariable("municipality",  clientGetDto.getMunicipality());
        context.setVariable("province",  clientGetDto.getProvince());
//        context.setVariables(model);
        String html = templateEngine.process("email-template", context);

        helper.setTo(clientGetDto.getEmail());
        helper.setText(html, true);
        helper.setSubject("Notification From Test Company");
        helper.setFrom(applicationProperties.getSupportEmail());

        emailSender.send(message);
    }


}
