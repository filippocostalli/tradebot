package it.costalli.tradebot.oanda.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import com.oanda.v20.primitives.DateTime;

import it.costalli.tradebot.model.TradeableInstrument;
import it.costalli.tradebot.service.MarketEventCallback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OandaPriceStreamingService {
	
	@Autowired
	Context oandaContext;
	
	boolean currentPollingActive = true;
	
	@Async
	public void startPriceDataStreaming(AccountID accountID, List<String> instruments, MarketEventCallback<String> marketEventCallback) {
	
		
		
		DateTime since = null;
		
		PricingGetRequest request = new PricingGetRequest(accountID, instruments);
		PricingGetResponse response;
			
		while(currentPollingActive) {
				
			try {
				response = oandaContext.pricing.get(request);
				since = response.getTime();
				
				for (ClientPrice price : response.getPrices()) { 
					
					final String instrument = price.getInstrument().toString();
					final String timeAsString = price.getTime().toString();
					final long eventTime = Long.parseLong(timeAsString);
					final double bidPrice = price.getCloseoutBid().doubleValue();
					final double askPrice = price.getCloseoutAsk().doubleValue();
					
					request.setSince(since);
					
					// TODO: ZONEDDATETIMNE INSTEAD OF NULL
					marketEventCallback.onMarketEvent(new TradeableInstrument<String>(instrument), bidPrice, askPrice, null);
				}
			} 
			catch (Exception e) {
				log.error("Error encountered inside market data streaming thread", e);
			} 
			finally {
				currentPollingActive = false;
			}
		}
		
	}
	
	public void stopPriceDataStreaming() {
		currentPollingActive = false;
		
	}
}