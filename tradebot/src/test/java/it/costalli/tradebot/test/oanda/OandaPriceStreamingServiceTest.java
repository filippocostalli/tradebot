package it.costalli.tradebot.test.oanda;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.costalli.tradebot.oanda.model.commons.CurrencyPair;
import it.costalli.tradebot.oanda.model.stream.PriceStreamMessage;
import it.costalli.tradebot.oanda.service.OandaPriceStreamingServiceFlux;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@DisplayName("Test Oanda v20 REST Api Client Price Stream")
@Slf4j
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.MethodName.class)
@ExtendWith(SpringExtension.class) // If we want to migrate this test to JUnit 5, we need to replace the @RunWith annotation with the new @ExtendWith
@SpringBootTest
class OandaPriceStreamingServiceTest {
	
	@Autowired
	OandaPriceStreamingServiceFlux oandaPriceStreamingServiceFlux;

	@Test
	void test001_getFluxStream() {
		try {
			List<String> fava = new ArrayList<String>();
			
			Flux.just("A", "B", "C", "D")
			.log()
			.subscribe( fava::add);
			
			String accountId = "101-012-22250783-001"; 
			
			Flux<PriceStreamMessage> flux =
					oandaPriceStreamingServiceFlux.get(
							accountId,
							Arrays.asList(new CurrencyPair(Currency.getInstance("EUR"), Currency.getInstance("GBP"))),
							true);
			
			assertNotNull(flux);
	        Flux<PriceStreamMessage> fivePrices = flux.take(5);
	        fivePrices.toStream().forEach(x -> log.info(x.toString()));
			
		}
		catch (Exception ex) {
			log.error("test001_getFluxStream", ex);
			fail(ex.getMessage());
		}
	}

}
