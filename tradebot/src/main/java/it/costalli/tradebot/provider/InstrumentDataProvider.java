package it.costalli.tradebot.provider;


import java.util.List;

import it.costalli.tradebot.exception.TradeException;
import it.costalli.tradebot.model.Account;
import it.costalli.tradebot.model.TradeableInstrument;

/**
 * A provider of tradeable instrument data information. At the very minimum the
 * provider must provide the instrument name and pip value for each instrument.
 * Since the instrument data almost never changes during trading hours, it is
 * highly recommended that the data returned from this provider is cached in an
 * immutable collection.
 * 
 * @author Shekhar Varshney
 *
 * @param <T>The type of instrumentId in class TradeableInstrument
 * @see TradeableInstrument
 */
public interface InstrumentDataProvider<T> {
	/**
	 * 
	 * @return a collection of all TradeableInstrument available to trade on the
	 *         brokerage platform.
	 */
	List<TradeableInstrument<T>> getInstruments(Account<T> account) throws TradeException, Exception;
}