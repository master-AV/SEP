//package com.sep.api.gateway.config;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//@Configuration
//// Injektovanje bean-a za bezbednost
//@EnableWebSecurity
//// Ukljucivanje podrske za anotacije "@Pre*" i "@Post*" koje ce aktivirati autorizacione provere za svaki pristup metodi
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
//public class WebSecurityConfig {
//
//    // metoda u kojoj se definisu putanje za igorisanje autentifikacije
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // Autentifikacija ce biti ignorisana ispod navedenih putanja (kako bismo ubrzali pristup resursima)
//        // Zahtevi koji se mecuju za web.ignoring().antMatchers() nemaju pristup SecurityContext-u
//        // Dozvoljena POST metoda na ruti /auth/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
//        return (web) -> web.ignoring()
//                .requestMatchers(HttpMethod.POST, "/id/login")
//                .requestMatchers(HttpMethod.GET, "/id/register")
//                //     .requestMatchers(HttpMethod.POST, "/device/message")
//
//                // Ovim smo dozvolili pristup statickim resursima aplikacije
//                .requestMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico",
//                        "/*/*.html", "/*/*.css", "/*/*.js");
//
//    }
//
//}