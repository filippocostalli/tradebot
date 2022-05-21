package it.costalli.tradebot.oanda.service;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oanda.v20.account.AccountID;


import it.costalli.tradebot.model.Account;
import it.costalli.tradebot.model.TradeableInstrument;
import it.costalli.tradebot.service.MarketDataStreamingService;
import it.costalli.tradebot.service.MarketEventCallback;

@Service
public class OandaMarketDataStreamingService implements MarketDataStreamingService<String> {

	@Autowired
	OandaPriceStreamingService oandaPriceStreamingService;


	@Override
	public void stopMarketDataStreaming() {
		oandaPriceStreamingService.stopPriceDataStreaming();
	}

	@Override
	public void startMarketDataStreaming(Account<String> account, Collection<TradeableInstrument<String>> tradeableInstruments, MarketEventCallback<String> marketEventCallback) {
		
		AccountID accountID = new AccountID(account.getAccountId()); 		
		List<String> instruments = tradeableInstruments.stream().map(x -> x.getInstrumentName()).collect(Collectors.toList());
					
		oandaPriceStreamingService.startPriceDataStreaming(accountID, instruments, marketEventCallback);
	}

}