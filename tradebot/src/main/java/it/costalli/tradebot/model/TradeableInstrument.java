package it.costalli.tradebot.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeableInstrument<T> {
	
	@NotNull
	private final String instrument;
	
	/**
	 * Represents an internal identifier for that instrument on the given platform.
	 * This may be required on some platforms to be passed in instead of the actual instrument code (like GBP_USD) for placing an order/trade. 
	 * It, along with instrument attribute, participates in the definition of hashCode() and equals().
	 */
	private final T instrumentId;
	
	/**
	 * The pip is the lowest precision at which the given instrument ticks. 
	 * For example, the pip value for USDJPY is 0.001, which means the minimum change in price for USDJPY would be 0.001. 
	 * If the current USDJPY price is 123.462, then at minimum the next price change would be to either 123.461 or 123.463.
	 */
	@NotNull
	private final Double pip;
	
	/**
	 * 
	 */
	private InstrumentPairInterestRate instrumentPairInterestRate;
	private final String description;

	public TradeableInstrument(String instrument) {
		this(instrument, null);
	}

	public TradeableInstrument(String instrument, String description) {
		this(instrument, null, description);
	}

	public TradeableInstrument(String instrument, T instrumentId, String description) {
		this(instrument, instrumentId, 0, null);
	}

	public TradeableInstrument(final String instrument, final double pip, InstrumentPairInterestRate instrumentPairInterestRate, String description) {
		this(instrument, null, pip, instrumentPairInterestRate, description);

	}

	public TradeableInstrument(final String instrument, T instrumentId, final double pip, InstrumentPairInterestRate instrumentPairInterestRate) {
		this(instrument, instrumentId, pip, instrumentPairInterestRate, null);
	}

}