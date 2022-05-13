package it.costalli.tradebot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class BaseTradingConfig {
	
	@Value("${basetrade.minReserveRatio}")
	private double minReserveRatio;
	
	@Value("${basetrade.minAmountRequired}")
	private double minAmountRequired;

	@Value("${basetrade.maxAllowedQuantity}")
	private int maxAllowedQuantity;
	
	@Value("${basetrade.maxAllowedNetContracts}")
	private int maxAllowedNetContracts;
	
	@Value("${basetrade.max10yrWmaOffset}")
	private double max10yrWmaOffset;
}