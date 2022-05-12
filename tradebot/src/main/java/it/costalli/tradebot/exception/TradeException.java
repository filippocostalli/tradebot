package it.costalli.tradebot.exception;




public class TradeException extends Exception { 
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1610733879871734685L;

	public TradeException(String errorMessage) {
        super(errorMessage);
    }
}