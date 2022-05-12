package it.costalli.tradebot.oanda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;

import it.costalli.tradebot.exception.TradeException;
import it.costalli.tradebot.model.Account;
import it.costalli.tradebot.service.AccountService;


@Service
public class OandaAccountService implements AccountService<String>{
	
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
			
			double accountNetAssetValue = accountMarginUsed + accountMarginAvailable;
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
		else {
			throw new TradeException("No account retrieved for id = " + accountId);
		}
		
		return account;
	}

	@Override
	public List<Account<String>> getLatestAccountInfo()  throws TradeException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
