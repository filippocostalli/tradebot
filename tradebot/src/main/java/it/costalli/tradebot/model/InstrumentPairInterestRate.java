package it.costalli.tradebot.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class InstrumentPairInterestRate {

	private final Double baseCurrencyBidInterestRate;
	private final Double baseCurrencyAskInterestRate;
	private final Double quoteCurrencyBidInterestRate;
	private final Double quoteCurrencyAskInterestRate;

	public InstrumentPairInterestRate() {
		this(null, null, null, null);
	}

}