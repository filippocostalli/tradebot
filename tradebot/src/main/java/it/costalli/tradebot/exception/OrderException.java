package it.costalli.tradebot.exception;

public class OrderException extends Exception{
	
	
	private static final long serialVersionUID = 58603053568718664L;

	public OrderException(Throwable t) {
        super(t);
    }
	
	public OrderException(String errorMessage) {
        super(errorMessage);
    }
	
	public OrderException(String message, Throwable t) {
        super(message, t);
    }

}
