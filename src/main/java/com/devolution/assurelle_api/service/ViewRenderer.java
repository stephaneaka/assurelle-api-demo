package com.devolution.assurelle_api.service;

import java.util.Map;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


public class ViewRenderer {

    public static String render(String templatePath, Map<String, Object> templateData){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setSuffix(".html");
        Context context = new Context();
		context.setVariables(templateData);
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
        return templateEngine.process(templatePath, context);
    }
}
