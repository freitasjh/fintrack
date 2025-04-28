package br.com.systec.fintrack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class I18nTranslate {

   private static ResourceBundleMessageSource messageSource;

   @Autowired
   I18nTranslate(ResourceBundleMessageSource resourceBundleMessageSource) {
      I18nTranslate.messageSource = resourceBundleMessageSource;
   }

   public static String toLocale(String msgCode) {
      Locale locale = LocaleContextHolder.getLocale();
      return messageSource.getMessage(msgCode, null, locale);
   }
}