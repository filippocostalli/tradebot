package it.costalli.tradebot.oanda.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.primitives.Instrument;

import it.costalli.tradebot.exception.TradeException;
import it.costalli.tradebot.model.Account;
import it.costalli.tradebot.model.InstrumentPairInterestRate;
import it.costalli.tradebot.model.TradeableInstrument;
import it.costalli.tradebot.provider.InstrumentDataProvider;


@Service
public class OandaInstrumentDataProvider implements InstrumentDataProvider<String> {


	@Autowired
	Context oandaContext;
	
	
	@Override
	public List<TradeableInstrument<String>> getInstruments(Account<String> account) throws TradeException, Exception{
		List<TradeableInstrument<String>> treadableInstruments = new ArrayList<TradeableInstrument<String>>();
		
		AccountID accountID = new AccountID(account.getAccountId());
		List<Instrument> oandaInstruments = oandaContext.account.instruments(accountID).getInstruments();
		if (oandaInstruments != null && oandaInstruments.size() > 0) {
			treadableInstruments = oandaInstruments
					.stream()
					.map(x -> this.getInstrumentPairInterestRate(x))
					.collect(Collectors.toList());
		}
		return treadableInstruments;
	}
	
	/**
	 * TODO: Oanda Instrument contains a lot of interesting and extremely useful fields. So I have to use them next! 
	 * {
			"name": "USD_JPY",
			"type": "CURRENCY",
			"displayName": "USD/JPY",
			"pipLocation": -2,
			"displayPrecision": 3,
			"tradeUnitsPrecision": 0,
			"minimumTradeSize": "1",
			"maximumTrailingStopDistance": "100.000",
			"minimumTrailingStopDistance": "0.050",
			"maximumPositionSize": "0",
			"maximumOrderUnits": "100000000",
			"marginRate": "0.0333",
			"guaranteedStopLossOrderMode": "ALLOWED",
			"minimumGuaranteedStopLossDistance": "0.05",
			"guaranteedStopLossOrderExecutionPremium": "0.020",
			"guaranteedStopLossOrderLevelRestriction": {
				"volume": "1000000",
				"priceRange": "0.250"
			},
			"tags": [
				{
					"type": "ASSET_CLASS",
					"name": "CURRENCY"
				}
			],
			"financing": {
				"longRate": "0.00060",
				"shortRate": "-0.0215",
				"financingDaysOfWeek": [
					{
						"dayOfWeek": "MONDAY",
						"daysCharged": 1
					},
					{
						"dayOfWeek": "TUESDAY",
						"daysCharged": 1
					},
					{
						"dayOfWeek": "WEDNESDAY",
						"daysCharged": 1
					},
					{
						"dayOfWeek": "THURSDAY",
						"daysCharged": 1
					},
					{
						"dayOfWeek": "FRIDAY",
						"daysCharged": 1
					},
					{
						"dayOfWeek": "SATURDAY",
						"daysCharged": 0
					},
					{
						"dayOfWeek": "SUNDAY",
						"daysCharged": 0
					}
				]
			}
		},
	 * 
	 * @param instrument
	 * @return
	 */
	private TradeableInstrument<String> getInstrumentPairInterestRate(Instrument instrument) {
		
		InstrumentPairInterestRate instrumentPairInterestRate = new InstrumentPairInterestRate();
		
		
		/*
		 * iNTEREST RATE DATA ARE MISSINg WITH THE V20 API?
		 * 
		 * https://www.reddit.com/r/algotrading/comments/90n0s3/question_on_oanda_v20_api_how_to_get_currency/
		 * 
		 * Question on Oanda V20 API: How to get currency pairs interest rate/swap rates using API?
		 * Would appreciate any help in this. I see that in there V1 API theres an interest rate output. But I cannot find any V20 endpoints corresponding to that.
		 */
		
		
		/*JSONObject interestRates = (JSONObject) instrumentJson.get(interestRate);
		JSONObject currency1Json = (JSONObject) interestRates.get(currencies[0]);
		JSONObject currency2Json = (JSONObject) interestRates.get(currencies[1]);
		final double baseCurrencyBidInterestRate = ((Number) currency1Json.get(bid)).doubleValue();
		final double baseCurrencyAskInterestRate = ((Number) currency1Json.get(ask)).doubleValue();
		final double quoteCurrencyBidInterestRate = ((Number) currency2Json.get(bid)).doubleValue();
		final double quoteCurrencyAskInterestRate = ((Number) currency2Json.get(ask)).doubleValue();

		InstrumentPairInterestRate instrumentPairInterestRate = new InstrumentPairInterestRate(
				baseCurrencyBidInterestRate, baseCurrencyAskInterestRate, quoteCurrencyBidInterestRate,
				quoteCurrencyAskInterestRate);*/
		
		Long pipLocation = instrument.getPipLocation();
		Double pip = Math.pow(10, pipLocation);
		String instrumentName = instrument.getName().toString();
		String description = instrument.getDisplayName();
		
		TradeableInstrument<String> tradeableInstrument = new TradeableInstrument<String>(instrumentName, pip, instrumentPairInterestRate, description);
		return tradeableInstrument;
	}

}