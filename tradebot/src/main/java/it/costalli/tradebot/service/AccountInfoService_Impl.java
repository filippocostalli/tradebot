package it.costalli.tradebot.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.costalli.tradebot.config.BaseTradingConfig;
import it.costalli.tradebot.exception.TradeException;
import it.costalli.tradebot.model.Account;
import it.costalli.tradebot.model.TradeableInstrument;
import it.costalli.tradebot.provider.AccountDataProvider;
import it.costalli.tradebot.utils.comparator.MarginAvailableComparator;

@Service
public class AccountInfoService_Impl<K, N> implements AccountInfoService<K, N> {
	
	@Autowired
	AccountDataProvider<K> accountDataProvider;
	
	@Autowired
	BaseTradingConfig baseTradingConfig;
	
	// TODO: to be implemented
	//CurrentPriceInfoProvider<N> currentPriceInfoProvider; // provider of current prices for a given set of instruments
	
	// TODO: to be implemented
	//private final ProviderHelper providerHelper;
	
	Comparator<Account<K>> accountComparator = new MarginAvailableComparator<K>();

	@Override
	public Collection<K> findAccountsToTrade() throws TradeException, Exception {
		
		List<Account<K>> accounts = accountDataProvider.getLatestAccountInfo();
		Collections.sort(accounts, accountComparator);
		List<K> accountsFound = accounts
				.stream()
				.filter(x -> x.getAmountAvailableRatio() >= baseTradingConfig.getMinReserveRatio() && x.getNetAssetValue() >= baseTradingConfig.getMinAmountRequired())
				.map(x -> x.getAccountId())
				.collect(Collectors.toList());
		return accountsFound;
	}

	@Override
	public double calculateMarginForTrade(Account<K> accountInfo, TradeableInstrument<N> instrument, int units) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double calculateMarginForTrade(K accountId, TradeableInstrument<N> instrument, int units) {
		// TODO Auto-generated method stub
		return 0;
	}

}
