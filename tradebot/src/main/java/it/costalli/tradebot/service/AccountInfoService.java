package it.costalli.tradebot.service;

import java.util.Collection;

import it.costalli.tradebot.exception.TradeException;
import it.costalli.tradebot.model.Account;
import it.costalli.tradebot.model.TradeableInstrument;




public interface AccountInfoService<K, N> {

	
	Collection<K> findAccountsToTrade() throws TradeException, Exception;

	double calculateMarginForTrade(Account<K> accountInfo, TradeableInstrument<N> instrument, int units);
	
	double calculateMarginForTrade(K accountId, TradeableInstrument<N> instrument, int units);
	
}
