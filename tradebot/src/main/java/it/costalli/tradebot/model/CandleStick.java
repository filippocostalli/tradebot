package it.costalli.tradebot.model;


import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CandleStick<T> {
	
	/*All prices are average of bid and ask ,i.e (bid+ask)/2*/
	private final double 
		openPrice, 
		highPrice, 
		lowPrice, 
		closePrice;
	
	private final DateTime eventDate;
	
	private final TradeableInstrument<T> instrument;
	
	private final CandleStickGranularity candleGranularity;
	
}