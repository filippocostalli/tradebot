package it.costalli.tradebot.oanda.model.commons;

import java.util.Currency;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CurrencyPair {
	
  private final Currency buyCurrency;
  
  private final Currency sellCurrency;
}