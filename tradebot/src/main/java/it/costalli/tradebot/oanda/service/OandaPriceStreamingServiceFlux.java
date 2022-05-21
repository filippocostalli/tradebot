package it.costalli.tradebot.oanda.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import it.costalli.tradebot.oanda.model.commons.CurrencyPair;
import it.costalli.tradebot.oanda.model.commons.CurrencyPairFormat;
import it.costalli.tradebot.oanda.model.stream.PriceStreamMessage;

import reactor.core.publisher.Flux;

@Service
public class OandaPriceStreamingServiceFlux {

	
	@Autowired
	WebClient oandaWebClient;

	 
	private final ServerResponseValidator validator = new ServerResponseValidator();

	  /**
	   * Get a live stream of prices for an instrument.
	   *
	   * @param instruments Instruments
	   * @return A stream of candlesticks
	   */
	  
	public Flux<PriceStreamMessage> get(String accountId, List<CurrencyPair> instruments, boolean validate) {
	    
		//String streamEndpoint = new MessageFormat("/v3/accounts/{0}/pricing/stream").format(new Object[] {accountId});
		
		String streamEndpoint = new MessageFormat("/v3/accounts/{0}/pricing").format(new Object[] {accountId});

	    return oandaWebClient
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
	                validator.validate(price);
	              }
	            });
	  }
}
