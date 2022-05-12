package it.costalli.tradebot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account<T> {
	
	private final double totalBalance;
	private final double unrealisedPnl;
	private final double realisedPnl;
	private final double marginUsed;
	private final double marginAvailable;
	private final double netAssetValue;
	private final long openTrades;
	private final String currency;
	private final T accountId;
	private final double amountAvailableRatio; /*The amount available to trade as a fraction of total amount*/
	private final double marginRate;           /*The leverage offered on this account. for e.g. 0.05, 0.1 etc*/
	
	
	
	/*
	 * account = new Account<String>(
										accountMarginUsed, 
					accountMarginAvailable, 
					accountOpenTrades, 
					accountBaseCurrency, 
					accountId,
					accountAmountAvailableRatio,
					accountLeverage);
	 */
	

}
