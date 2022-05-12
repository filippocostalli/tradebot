package it.costalli.tradebot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class BaseTradingConfig {
	
	@Value("${config.minReserveRatio}")
	private double minReserveRatio;
	
	@Value("${config.minAmountRequired}")
	private double minAmountRequired;

	@Value("${config.maxAllowedQuantity}")
	private int maxAllowedQuantity;
	
	@Value("${config.maxAllowedNetContracts}")
	private int maxAllowedNetContracts;
	
	@Value("${config.max10yrWmaOffset}")
	private double max10yrWmaOffset;
}