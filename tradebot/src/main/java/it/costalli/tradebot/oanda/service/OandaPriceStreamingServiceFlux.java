package it.costalli.tradebot.oanda.service;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import it.costalli.tradebot.oanda.model.commons.CurrencyPair;
import it.costalli.tradebot.oanda.model.commons.CurrencyPairFormat;
import it.costalli.tradebot.oanda.model.stream.PriceStreamMessage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class OandaPriceStreamingServiceFlux {

	
	@Autowired
	WebClient oandaStreamWebClient;

	 
	private final ServerResponseValidator validator = new ServerResponseValidator();

	  /**
	   * Get a live stream of prices for an instrument.
	   *
	   * @param instruments Instruments
	   * @return A stream of candlesticks
	   */
	  
	public Flux<PriceStreamMessage> get(String accountId, List<CurrencyPair> instruments, boolean validate) {
	    
		String streamEndpoint = new MessageFormat("/v3/accounts/{0}/pricing/stream").format(new Object[] {accountId});
		
	    return oandaStreamWebClient
	        .get()
	        .uri(
	            builder ->
	                builder
	                    .path(streamEndpoint)
	                    //.queryParam("snapshot", true)
	                    .queryParam(
	                        "instruments",
	                        instruments.stream()
	                            .map(instrument ->CurrencyPairFormat.format(instrument, CurrencyPairFormat.OANDA_FORMAT))
	                            .collect(Collectors.toList())
	                            .toArray())
	                    .build())
	        .retrieve()
	        .bodyToFlux(PriceStreamMessage.class)
	        .doOnNext(
	            price -> {
	              if (validate) {
	            	  log.info("Valido");
	                validator.validate(price);
	              }
	            });
	  }
	
	
	public Flux<String> getFluxString(String accountId, List<CurrencyPair> instruments, boolean validate) {
	    
		String streamEndpoint = new MessageFormat("/v3/accounts/{0}/pricing/stream").format(new Object[] {accountId});
		
	    return oandaStreamWebClient
	        .get()
	        .uri(
	            builder ->
	                builder
	                    .path(streamEndpoint)
	                    .queryParam("stream", true)
	                    .queryParam(
	                        "instruments",
	                        instruments.stream()
	                            .map(instrument ->CurrencyPairFormat.format(instrument, CurrencyPairFormat.OANDA_FORMAT))
	                            .collect(Collectors.toList())
	                            .toArray())
	                    .build())
	        .retrieve()
	        .bodyToFlux(String.class)
	        .doOnNext(
	            price -> {
	            	  log.info("Valido " + price);
	            });
	  }
	
public Flux<String> getFluxStringTimeout(String accountId, List<CurrencyPair> instruments, boolean validate) {
	    
		String streamEndpoint = new MessageFormat("/v3/accounts/{0}/pricing/stream").format(new Object[] {accountId});
		
	    return oandaStreamWebClient
	        .get()
	        .uri(
	            builder ->
	                builder
	                    .path(streamEndpoint)
	                    .queryParam("stream", true)
	                    .queryParam(
	                        "instruments",
	                        instruments.stream()
	                            .map(instrument ->CurrencyPairFormat.format(instrument, CurrencyPairFormat.OANDA_FORMAT))
	                            .collect(Collectors.toList())
	                            .toArray())
	                    .build())
	        .retrieve()
	        .bodyToFlux(String.class)
	        //.timeout(Duration.ofMillis(1000))
            //.retry(2)
            //.onErrorReturn("default")
	        .doOnNext(
	            price -> {
	            	  log.info("Valido " + price);
	            });
	  }
}
