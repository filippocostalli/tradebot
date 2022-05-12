package it.costalli.tradebot.oanda.provider;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountListResponse;
import com.oanda.v20.account.AccountProperties;
import com.oanda.v20.account.AccountSummary;
import com.pivovarit.function.ThrowingFunction;

import it.costalli.tradebot.exception.TradeException;
import it.costalli.tradebot.model.Account;
import it.costalli.tradebot.provider.AccountDataProvider;


@Component
public class OandaAccountIDataProvider implements AccountDataProvider<String>{
	
	@Autowired
	Context oandaContext;

	@Override
	public Account<String> getLatestAccountInfo(String accountId)  throws TradeException, Exception {
		Account<String> account = null;
		AccountID accountID = new AccountID(accountId);
		AccountSummary accountSummary = oandaContext.account.summary(accountID).getAccount();
		
		if (accountSummary != null) {
			final double accountBalance = accountSummary.getBalance().doubleValue();
			final double accountUnrealizedPnl = accountSummary.getUnrealizedPL().doubleValue();
			final double accountRealizedPnl = accountSummary.getPl().doubleValue();
			final double accountMarginUsed = accountSummary.getMarginUsed().doubleValue();
			final double accountMarginAvailable = accountSummary.getMarginAvailable().doubleValue();
			final Long accountOpenTrades = accountSummary.getOpenTradeCount();
			final String accountBaseCurrency = accountSummary.getCurrency().toString();
			final double accountLeverage = accountSummary.getMarginRate().doubleValue();
			
			final double accountNetAssetValue = accountMarginUsed + accountMarginAvailable;
			final double accountAmountAvailableRatio = accountMarginAvailable / accountBalance;
		
			account = new Account<String>(
					accountBalance, 
					accountUnrealizedPnl, 
					accountRealizedPnl,
					accountMarginUsed,  
					accountMarginAvailable, 
					accountNetAssetValue,
					accountOpenTrades, 
					accountBaseCurrency, 
					accountId,
					accountAmountAvailableRatio,
					accountLeverage);
		}
		return account;
	}

	@Override
	public List<Account<String>> getLatestAccountInfo()  throws TradeException, Exception {
		
		AccountListResponse response = oandaContext.account.list();
		List<AccountProperties> accountProperties = response.getAccounts();
		List<Account<String>> accounts = accountProperties
				.stream()
				.map(x -> x.getId().toString())
				.map(ThrowingFunction.unchecked(x -> this.getLatestAccountInfo(x)))
				.collect(Collectors.toList());
		
		return accounts;
	}

}