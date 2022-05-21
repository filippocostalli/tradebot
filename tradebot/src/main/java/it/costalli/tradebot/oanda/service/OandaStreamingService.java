package it.costalli.tradebot.oanda.service;



import it.costalli.tradebot.service.HeartBeatStreamingService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class OandaStreamingService implements HeartBeatStreamingService {
	
	protected volatile boolean serviceUp = true;
	
	protected abstract void startStreaming();

	protected abstract void stopStreaming();
	
	//private final HeartBeatCallback<ZonedDateTime> heartBeatCallback;

	/*
	protected void handleHeartBeat(JSONObject streamEvent) {
		Long t = Long.parseLong(((JSONObject) streamEvent.get(heartbeat)).get(time).toString());
		heartBeatCallback.onHeartBeat(new HeartBeatPayLoad<DateTime>(new DateTime(TradingUtils.toMillisFromNanos(t)),
				hearbeatSourceId));
	}
	*/


	protected void handleDisconnect(String line) {
		serviceUp = false;
		log.warn(String.format("Disconnect message received for stream %s. PayLoad->%s", getHeartBeatSourceId(), line));
	}

	protected boolean isStreaming() {
		return serviceUp;
	}

	@Override
	public void stopHeartBeatStreaming() {
		stopStreaming();
	}

	@Override
	public void startHeartBeatStreaming() {
		startStreaming();
	}

	@Override
	public String getHeartBeatSourceId() {
		//return this.hearbeatSourceId;
		return null;
	}
}