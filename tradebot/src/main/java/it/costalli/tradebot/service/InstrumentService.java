package it.costalli.tradebot.service;

import java.util.List;

import it.costalli.tradebot.exception.TradeException;
import it.costalli.tradebot.model.Account;
import it.costalli.tradebot.model.TradeableInstrument;

public interface InstrumentService<T> {
	
	List<TradeableInstrument<T>> getInstruments(Account<T> account) throws TradeException, Exception;

	List<TradeableInstrument<T>> getAllPairsWithCurrency(Account<T> account, String currency) throws TradeException, Exception;

	public Double getPipForInstrument(Account<T> account, TradeableInstrument<T> instrument) throws TradeException, Exception;
}