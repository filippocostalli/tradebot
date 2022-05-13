package it.costalli.tradebot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import it.costalli.tradebot.exception.TradeException;
import it.costalli.tradebot.model.Account;
import it.costalli.tradebot.model.TradeableInstrument;
import it.costalli.tradebot.provider.InstrumentDataProvider;

@Service
public class InstrumentService_Impl<T> implements InstrumentService<T> {
	
	
	@Autowired
	InstrumentDataProvider<T> instrumentDataProvider;
	
	

	@Cacheable(value = "accountInstruments", key = "#account.accountId", sync = true)
	@Override
	public List<TradeableInstrument<T>> getInstruments(Account<T> account) throws TradeException, Exception {
		return instrumentDataProvider.getInstruments(account);
	}

	
	@Override
	public List<TradeableInstrument<T>> getAllPairsWithCurrency(Account<T> account, String currency) throws TradeException, Exception {
		return this.getInstruments(account)
				.stream()
				.filter(x -> x.getInstrumentName().contains(currency))
				.collect(Collectors.toList());
	}
	

	@Override
	public Double getPipForInstrument(Account<T> account, TradeableInstrument<T> instrument) throws TradeException, Exception {
		TradeableInstrument<T> accountInstrument = this.getInstruments(account)
				.stream()
				.filter(x -> x.getInstrumentName().equals(instrument.getInstrumentName()))
				.findFirst()
				.orElse(null);
		return (accountInstrument != null)?accountInstrument.getPip():0.1;
	}

}
