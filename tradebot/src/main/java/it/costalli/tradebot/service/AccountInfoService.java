package it.costalli.tradebot.service;

import java.util.Collection;

import it.costalli.tradebot.model.Account;


public interface AccountInfoService<K, N> {

	
	Collection<K> findAccountsToTrade();
	
	//double calculateMarginForTrade(Account<K> accountInfo, TradeableInstrument<N> instrument, int units);
	
	//double calculateMarginForTrade(K accountId, TradeableInstrument<N> instrument, int units);
	
}
