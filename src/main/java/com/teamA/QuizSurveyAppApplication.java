package com.teamA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@SpringBootApplication
public class QuizSurveyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizSurveyAppApplication.class, args);
	}


	@Bean
	public HttpFirewall allowSomeSymbolsHttpFirewall(){
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowUrlEncodedDoubleSlash(true);
		return firewall;
	}
}
