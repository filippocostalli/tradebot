package it.costalli.tradebot.service;

import java.util.Collection;

import it.costalli.tradebot.exception.TradeException;




public interface AccountInfoService<K, N> {

	
	Collection<K> findAccountsToTrade() throws TradeException, Exception;
	
	// TODO: TradeableInstrument
	//double calculateMarginForTrade(Account<K> accountInfo, TradeableInstrument<N> instrument, int units);
	
	//double calculateMarginForTrade(K accountId, TradeableInstrument<N> instrument, int units);
	
}
