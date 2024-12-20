package com.digital.wallet.service.mail;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class MailTemplateService {
    private final Configuration configuration;

    protected String prepareTemplateModelSetup(final String mailTemplate, final Map<String, String> valueMap) {
        String content = mailTemplate;
        for (String key : valueMap.keySet()) {
            String placeholder = "${" + key + "}";
            String replacedValue = valueMap.get(key) != null ? valueMap.get(key) : StringUtil.EMPTY_STRING;
            if (content.contains(placeholder))
                content = content.replace(placeholder, replacedValue);
        }
        if (content.contains("${")) {
            content = content.replaceAll(".\\Q${\\E.*\\Q}\\E", StringUtil.EMPTY_STRING);
        }
        return content;
    }

    protected String getEmailContent(String templateName, Map<String, String> model) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        configuration.getTemplate(templateName).process(model, stringWriter);
        String mailTemplate = stringWriter.getBuffer().toString();
        if (mailTemplate.contains("${")) {
            mailTemplate = mailTemplate.replaceAll(".\\Q${\\E.*\\Q}\\E", StringUtil.EMPTY_STRING);
        }
        return mailTemplate;
    }
}
