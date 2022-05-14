package it.costalli.tradebot.service;

import java.time.ZonedDateTime;

import it.costalli.tradebot.model.TradeableInstrument;

/**
 * A callback handler for a market data event. The separate streaming event
 * handler upstream, is responsible for handling and parsing the incoming event
 * from the market data source and invoke the onMarketEvent of this handler,
 * which in turn can disseminate the event if required, further downstream.
 * Ideally, the implementer of this interface, would drop the event on a queue
 * for asynchronous processing or use an event bus for synchronous processing.
 * 
 * @author Shekhar Varshney
 *
 * @param <T>
 *            The type of instrumentId in class TradeableInstrument
 * @see TradeableInstrument
 * @see EventBus
 */
public interface MarketEventCallback<T> {
	/**
	 * A method, invoked by the upstream handler of streaming market data
	 * events. This invocation of this method is synchronous, therefore the
	 * method should return asap, to make sure that the upstream events do not
	 * queue up.
	 * 
	 * @param instrument
	 * @param bid
	 * @param ask
	 * @param eventDate
	 */
	void onMarketEvent(TradeableInstrument<T> instrument, double bid, double ask, ZonedDateTime eventDate);
}