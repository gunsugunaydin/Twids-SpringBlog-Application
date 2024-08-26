package org.twids.SpringBlog.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class AppConfig {
    
    //mailSender
    @Value("${spring.mail.host}")
    private String spring_mail_host;

    @Value("${spring.mail.port}")
    private String spring_mail_port;
    
    @Value("${spring.mail.username}")
    private String spring_mail_username;
    
    @Value("${spring.mail.password}")
    private String spring_mail_password;
    
    //prop
    @Value("${mail.transport.protocol}")
    private String mail_transport_protocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mail_smtp_auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mail_smtp_starttls_enable;
    
    @Value("${spring.mail.smtp.ssl.trust}")
    private String smtp_ssl_trust;

    //mailin gönderilmesi için gerekli mail ayarları
    @Bean
    public JavaMailSender getJavaMailSender(){
        
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        //mail sunucusu için host ve port bilgileri ayarlanıyor
        mailSender.setHost(spring_mail_host);
        mailSender.setPort(Integer.parseInt(spring_mail_port));
        
        //mail gönderimi için kullanıcı adı ve şifre ayarlanıyor
        mailSender.setUsername(spring_mail_username);
        mailSender.setPassword(spring_mail_password);

        //mail özellikleri (properties) ayarlanıyor
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mail_transport_protocol); //kullanılacak protokol
        props.put("mail.smtp.auth", mail_smtp_auth); //SMTP yetkilendirme
        props.put("mail.smtp.starttls.enable", mail_smtp_starttls_enable); // TLS güvenliği
        props.put("mail.debug", "true"); //debug modu
        props.put("mail.smtp.ssl.trust", smtp_ssl_trust); //SSL güvenliği için güvenilen sunucular

        //EHLO/HELO için geçerli bir hostname ayarlama
        props.put("mail.smtp.localhost", "Gunsu");

        return mailSender;
    }
}
