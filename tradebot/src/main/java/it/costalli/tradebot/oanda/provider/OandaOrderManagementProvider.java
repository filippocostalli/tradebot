package it.costalli.tradebot.oanda.provider;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.order.LimitOrderRequest;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.transaction.TransactionID;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;

import it.costalli.tradebot.exception.OrderException;
import it.costalli.tradebot.model.Order;
import it.costalli.tradebot.model.OrderType;
import it.costalli.tradebot.model.TradeableInstrument;
import it.costalli.tradebot.model.TradingSignal;
import it.costalli.tradebot.provider.OrderManagementProvider;
//import lombok.extern.slf4j.Slf4j;

@Service
public class OandaOrderManagementProvider implements OrderManagementProvider<String, String, String> {

	@Autowired
	Context oandaContext;

	@Override
	public boolean closeOrder(String orderId, String accountId) {
		/*
		CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpDelete httpDelete = new HttpDelete(orderForAccountUrl(accountId, orderId));
			httpDelete.setHeader(authHeader);
			LOG.info(TradingUtils.executingRequestMsg(httpDelete));
			HttpResponse resp = httpClient.execute(httpDelete);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				log.info(String.format("Order %d successfully deleted for account %d", orderId, accountId));
				return true;
			} else {
				log.warn(String.format("Order %d could not be deleted. Recd error code %d", orderId, resp.getStatusLine().getStatusCode()));
			}
		} catch (Exception e) {
			log.warn("error deleting order id:" + orderId, e);
		} finally {
			TradingUtils.closeSilently(httpClient);
		}
		*/
		return false;
	}

	/*
	HttpPatch createPatchCommand(Order<String, Long> order, Long accountId) throws Exception {
		HttpPatch httpPatch = new HttpPatch(orderForAccountUrl(accountId, order.getOrderId()));
		httpPatch.setHeader(this.authHeader);
		List<NameValuePair> params = Lists.newArrayList();
		params.add(new BasicNameValuePair(takeProfit, String.valueOf(order.getTakeProfit())));
		params.add(new BasicNameValuePair(stopLoss, String.valueOf(order.getStopLoss())));
		params.add(new BasicNameValuePair(units, String.valueOf(order.getUnits())));
		params.add(new BasicNameValuePair(price, String.valueOf(order.getPrice())));
		httpPatch.setEntity(new UrlEncodedFormEntity(params));
		return httpPatch;
	}
	*/

	/*
	HttpPost createPostCommand(Order<String, Long> order, Long accountId) throws Exception {
		HttpPost httpPost = new HttpPost(this.url + OandaConstants.ACCOUNTS_RESOURCE + TradingConstants.FWD_SLASH
				+ accountId + ordersResource);
		httpPost.setHeader(this.authHeader);
		List<NameValuePair> params = Lists.newArrayList();
		// TODO: apply proper rounding. Oanda rejects 0.960000001
		params.add(new BasicNameValuePair(instrument, order.getInstrument().getInstrumentName()));
		params.add(new BasicNameValuePair(side, OandaUtils.toSide(order.getSide())));
		params.add(new BasicNameValuePair(type, OandaUtils.toType(order.getType())));
		params.add(new BasicNameValuePair(units, String.valueOf(order.getUnits())));
		params.add(new BasicNameValuePair(takeProfit, String.valueOf(order.getTakeProfit())));
		params.add(new BasicNameValuePair(stopLoss, String.valueOf(order.getStopLoss())));
		if (order.getType() == OrderType.LIMIT && order.getPrice() != 0.0) {
			DateTime now = DateTime.now();
			DateTime nowplus4hrs = now.plusHours(4);// TODO: why this code
													// for expiry?
			String dateStr = nowplus4hrs.toString();
			params.add(new BasicNameValuePair(price, String.valueOf(order.getPrice())));
			params.add(new BasicNameValuePair(expiry, dateStr));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(params));
		return httpPost;
	}
	*/
	
	// M placeOrder(Order<N, M> order, K accountId);
	
	

	@Override
	public String placeOrder(Order<String, String> order, String accountId) throws OrderException, Exception{
		
		AccountID accountID = new AccountID(accountId);
		
		TransactionID tradeId;
		
		try {
			Long units = order.getUnits();
			if (units != null && order.getSide() == TradingSignal.SHORT) {
				units = -order.getUnits();
			}
			
			OrderCreateRequest request = new OrderCreateRequest(accountID);

			OrderType orderType = order.getType();
			switch (orderType) {
				case LIMIT:
					LimitOrderRequest limitOrderRequest = new LimitOrderRequest();
					limitOrderRequest.setUnits(order.getUnits());
					limitOrderRequest.setInstrument(order.getInstrument().getInstrumentName());
					limitOrderRequest.setPrice(order.getPrice());
					// TODO: TIME IN FORCE AND TIME THE ORDER WILL BE DELETED
					request.setOrder(limitOrderRequest);
					break;
					
				case MARKET:
					default:
					MarketOrderRequest marketOrderRequest = new MarketOrderRequest();
					marketOrderRequest.setUnits(order.getUnits());
					marketOrderRequest.setInstrument(order.getInstrument().getInstrumentName());
					request.setOrder(marketOrderRequest);
					break;
			}

			OrderCreateResponse response = oandaContext.order.create(request);
			tradeId = response.getOrderFillTransaction().getId();
			order.setOrderId(tradeId.toString());
			
			// TODO: get other params from response? I suppose so.
		}
		catch ( RequestException | ExecuteException e) {
			throw new OrderException(e);
		}
		return tradeId.toString();
	}

	@Override
	public Order<String, String> pendingOrderForAccount(String orderId, String accountId) {
		/*CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpUriRequest httpGet = new HttpGet(orderForAccountUrl(accountId, orderId));
			httpGet.setHeader(this.authHeader);
			httpGet.setHeader(OandaConstants.UNIX_DATETIME_HEADER);
			LOG.info(TradingUtils.executingRequestMsg(httpGet));
			HttpResponse resp = httpClient.execute(httpGet);
			String strResp = TradingUtils.responseToString(resp);
			if (strResp != StringUtils.EMPTY) {
				JSONObject order = (JSONObject) JSONValue.parse(strResp);
				return parseOrder(order);
			} else {
				TradingUtils.printErrorMsg(resp);
			}
		} catch (Exception e) {
			LOG.error(String.format("error encountered whilst fetching pending order for account %d and order id %d",
					accountId, orderId), e);
		} finally {
			TradingUtils.closeSilently(httpClient);
		}*/
		return null;
	}


	@Override
	public Collection<Order<String, String>> pendingOrdersForInstrument(TradeableInstrument<String> instrument) {
		/*Collection<Account<Long>> accounts = this.accountDataProvider.getLatestAccountInfo();
		Collection<Order<String, Long>> allOrders = Lists.newArrayList();
		for (Account<Long> account : accounts) {
			allOrders.addAll(this.pendingOrdersForAccount(account.getAccountId(), instrument));
		}
		return allOrders;*/
		return null;
	}

	@Override
	public Collection<Order<String, String>> allPendingOrders() {
		return pendingOrdersForInstrument(null);
	}

	private Collection<Order<String, String>> pendingOrdersForAccount(String accountId, TradeableInstrument<String> instrument) {
		/*Collection<Order<String, Long>> pendingOrders = Lists.newArrayList();
		CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpUriRequest httpGet = new HttpGet(this.url + OandaConstants.ACCOUNTS_RESOURCE
					+ TradingConstants.FWD_SLASH + accountId + ordersResource
					+ (instrument != null ? "?instrument=" + instrument.getInstrument() : StringUtils.EMPTY));
			httpGet.setHeader(this.authHeader);
			httpGet.setHeader(OandaConstants.UNIX_DATETIME_HEADER);
			LOG.info(TradingUtils.executingRequestMsg(httpGet));
			HttpResponse resp = httpClient.execute(httpGet);
			String strResp = TradingUtils.responseToString(resp);
			if (strResp != StringUtils.EMPTY) {
				Object obj = JSONValue.parse(strResp);
				JSONObject jsonResp = (JSONObject) obj;
				JSONArray accountOrders = (JSONArray) jsonResp.get(orders);

				for (Object o : accountOrders) {
					JSONObject order = (JSONObject) o;
					Order<String, Long> pendingOrder = parseOrder(order);
					pendingOrders.add(pendingOrder);
				}
			} else {
				TradingUtils.printErrorMsg(resp);
			}
		} catch (Exception e) {
			LOG.error(String.format("error encountered whilst fetching pending orders for account %d and instrument %s",
					accountId, instrument.getInstrument()), e);
		} finally {
			TradingUtils.closeSilently(httpClient);
		}
		return pendingOrders;*/
		return null;
	}

	@Override
	public Collection<Order<String, String>> pendingOrdersForAccount(String accountId) {
		return this.pendingOrdersForAccount(accountId, null);
	}

	
	@Override
	public boolean modifyOrder(Order<String, String> order, String accountId) {
		/*CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpPatch httpPatch = createPatchCommand(order, accountId);
			LOG.info(TradingUtils.executingRequestMsg(httpPatch));
			HttpResponse resp = httpClient.execute(httpPatch);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK && resp.getEntity() != null) {
				LOG.info("Order Modified->" + TradingUtils.responseToString(resp));
				return true;
			}
			LOG.warn(String.format("order %s could not be modified.", order.toString()));
		} catch (Exception e) {
			LOG.error(String.format("error encountered whilst modifying order %d for account %d", order, accountId), e);
		} finally {
			TradingUtils.closeSilently(httpClient);
		}*/
		return false;
	}

	

	

}