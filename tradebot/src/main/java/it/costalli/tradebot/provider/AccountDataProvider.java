package it.costalli.tradebot.provider;

import java.util.List;

import it.costalli.tradebot.exception.TradeException;
import it.costalli.tradebot.model.Account;

public interface AccountDataProvider<T> {

	
	/**
	 * 
	 * @param accountId
	 * @return Account information for the given accountId
	 */
	Account<T> getLatestAccountInfo(T accountId) throws TradeException, Exception;

	/**
	 * 
	 * @return A collection of ALL accounts available
	 */
	List<Account<T>> getLatestAccountInfo()  throws TradeException, Exception;
}
