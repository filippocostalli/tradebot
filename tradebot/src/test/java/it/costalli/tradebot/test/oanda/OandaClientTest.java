package it.costalli.tradebot.test.oanda;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountListResponse;
import com.oanda.v20.account.AccountProperties;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.primitives.Instrument;

import lombok.extern.slf4j.Slf4j;



/**
 * 
 * If you are using JUnit 4, don’t forget to add @RunWith(SpringRunner.class)to your test, otherwise the annotations will be ignored. 
 * If you are using JUnit 5, there’s no need to add the equivalent @ExtendWith(SpringExtension.class) as @SpringBootTest and the other @…Testannotations are already annotated with it
 *
 * @TestFactory – denotes a method that's a test factory for dynamic tests
 * @DisplayName – defines a custom display name for a test class or a test method
 * @Nested – denotes that the annotated class is a nested, non-static test class
 * @Tag – declares tags for filtering tests
 * @ExtendWith – registers custom extensions
 * @BeforeEach – denotes that the annotated method will be executed before each test method (previously @Before)
 * @AfterEach – denotes that the annotated method will be executed after each test method (previously @After)
 * @BeforeAll – denotes that the annotated method will be executed before all test methods in the current class (previously @BeforeClass)
 * @AfterAll – denotes that the annotated method will be executed after all test methods in the current class (previously @AfterClass)
 * @Disable – disables a test class or method (previously @Ignore)
 * 
 * Assertions have been moved to org.junit.jupiter.api.Assertions, and have been significantly improved. As mentioned earlier, we can now use lambdas in assertions
 */ 

@DisplayName("Test Oanda v20 REST Api Client")
@Slf4j
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.MethodName.class)
@ExtendWith(SpringExtension.class) // If we want to migrate this test to JUnit 5, we need to replace the @RunWith annotation with the new @ExtendWith
@SpringBootTest
class OandaClientTest {
	
	@Value("${oanda.apitoken}")
	String oandaToken;
	
	@Value("${oanda.account}")
	String oandaUsername;
	
	@Value("${oanda.url}")
	String oandaUrl;
	
	@Autowired
	Context oandaContext;

	@Test
	void test001_getAccountSummary() {
		try {
			assertNotNull(oandaContext);
			
			AccountListResponse response = oandaContext.account.list();
			assertNotNull(response);

			List<AccountProperties> accountProperties = response.getAccounts();
			assertNotNull(accountProperties);
			assertEquals(accountProperties.size(), 1);
			
			AccountProperties accountProps = accountProperties.get(0);
			log.info(accountProps.getId().toString());

			AccountID accountID = new AccountID("101-012-22250783-001");
			AccountSummary accountSummary = oandaContext.account.summary(accountID).getAccount(); //
			assertNotNull(accountSummary);
			log.info(accountSummary.toString());
			//log.info("" + accountSummary.getMarginAvailable().bigDecimalValue());
		}
		catch (Exception e) {
			log.error("test001_getAccountSummary", e);
			fail (e.getMessage());
		}
	}
	
	@Test
	void test002_getTreadableInstrument() {
		try {
			assertNotNull(oandaContext);
			

			AccountID accountID = new AccountID("101-012-22250783-001");
			List<Instrument> instruments = oandaContext.account.instruments(accountID).getInstruments();
			assertNotNull(instruments);
			assertTrue(instruments.size() > 0);
			for (Instrument instrument : instruments) {
				log.info(instrument.toString());
			}
			
		}
		catch (Exception e) {
			log.error("test002_getTreadableInstrument", e);
			fail (e.getMessage());
		}
	}

}
