package it.costalli.tradebot.service;

import java.time.ZonedDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.costalli.tradebot.model.TradeableInstrument;

@Component
public class MarketEventCallbackRabbitMQ<T> implements MarketEventCallback<T> {
	
	@Autowired
	RabbitTemplate rabbitTemplate;

	@Override
	public void onMarketEvent(TradeableInstrument<T> instrument, double bid, double ask, ZonedDateTime eventDate) {
		// TODO Auto-generated method stub
		
	}

}
