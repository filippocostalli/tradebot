package it.costalli.tradebot.oanda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import org.springframework.web.reactive.function.client.WebClient;

import com.google.common.net.HttpHeaders;

@Configuration
@PropertySource({"classpath:oanda.properties"})
public class WebClientConfiguration {
	
	@Value("${oanda.apitoken}")
	String oandaToken;
	
	@Value("${oanda.url}")
	String oandaUrl;
	
	@Bean(name = "oandaWebClient")
	public WebClient farmastatWebClient() {
		
		WebClient webClient = WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector())
		        .baseUrl(oandaUrl)
		        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + oandaToken)
		        .build();
		
		return webClient;
	}
	
	// httpReq.addHeader("Authorization","Bearer " + this.token);

}