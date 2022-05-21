package it.costalli.tradebot.service;

/**
 * A service that provides streaming heartbeats from the trading platform. The
 * service provided in the end by the platform may not be streaming at all but
 * some sort of regular callbacks in order to indicate that the connection is
 * alive. A loss of heartbeats may indicate a general failure to receive any
 * trade/order events and/or market data from the trading platform. Therefore
 * any monitoring of the application may involve directly interacting with this
 * service to raise alerts/notifications.
 * 
 * @author Shekhar Varshney
 *
 */
public interface HeartBeatStreamingService {

	/**
	 * Start the service in order to receive heartbeats from the trading
	 * platform. Ideally the implementation would make sure that multiple
	 * heartbeat connections/handlers are not created for the same kind of
	 * service. Depending on the trading platform, there may be a single
	 * heartbeat for all services or a dedicated one for services such as market
	 * data, trade/order events etc.
	 */
	void startHeartBeatStreaming();

	/**
	 * Stop the service in order to stop receiving heartbeats. The
	 * implementation must dispose any resources/connections in a suitable
	 * manner so as not to cause any resource leaks.
	 */
	void stopHeartBeatStreaming();

	/**
	 * 
	 * @return heartBeat source id which identifies the source for which this
	 *         service is providing heartbeats. This is useful to keep track all
	 *         sources which are heartbeating and can be individually monitored
	 *         on a regular basis. On some platforms there may be a dedicated
	 *         single heartbeat service for ALL in which case this may not be as
	 *         useful.
	 */
	String getHeartBeatSourceId();
}
