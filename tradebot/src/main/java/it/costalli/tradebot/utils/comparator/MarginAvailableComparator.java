package it.costalli.tradebot.utils.comparator;

import java.util.Comparator;

import it.costalli.tradebot.model.Account;

public class MarginAvailableComparator<K> implements Comparator<Account<K>> {

	@Override
	public int compare(Account<K> ai1, Account<K> ai2) {
		if (ai1.getMarginAvailable() > ai2.getMarginAvailable()) {
			return -1;
		} 
		else if (ai1.getMarginAvailable() < ai2.getMarginAvailable()) {
			return 1;
		}
		return 0;
	}

}