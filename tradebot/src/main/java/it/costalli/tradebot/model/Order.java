package it.costalli.tradebot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Order<M, N> {
	
	private final TradeableInstrument<M> instrument;
	
	private final long units;
	
	private final TradingSignal side;
	private final OrderType type;
	
	private final Double takeProfit;
	private final Double stopLoss;
	
	private N orderId;
	
	private final Double price;

	
	public void setOrderId(N orderId) {
		this.orderId = orderId;
	}

}

