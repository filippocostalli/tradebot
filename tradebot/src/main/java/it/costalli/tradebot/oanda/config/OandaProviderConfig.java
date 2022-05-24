package it.costalli.tradebot.oanda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;

@Configuration
@PropertySource({"classpath:oanda-${spring.profiles.active}.properties"})
public class OandaProviderConfig {
	
	@Value("${oanda.apitoken}")
	String oandaToken;
	
	@Value("${oanda.account}")
	String oandaUsername;
	
	@Value("${oanda.url}")
	String oandaUrl;
	
	
	
	@Bean(name = "oandaContext", destroyMethod = "")
	public Context oandaContext() throws Exception {
		
		Context ctx = new ContextBuilder(oandaUrl)
        		.setToken(oandaToken)
        		.setApplication("Filippo70")
        		.build();
		
		return ctx;
	} 

}
