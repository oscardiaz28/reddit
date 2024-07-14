package com.spring.reddit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Build the content of email using a thymeleaf template
 */
@Service
@AllArgsConstructor
public class MailContentBuilder {
    private final TemplateEngine templateEngine;

    // access from only the same package (default)
    String build(String message){
        Context context = new Context(); //Crea un nuevo contexto de thymeleaf
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }
}
