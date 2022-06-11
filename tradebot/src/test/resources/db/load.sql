INSERT INTO instrument (instrument_id, instrument_name) VALUES (1, 'EUR_USD');


COPY candle (candle_unix, candle_open, candle_max, candle_min, candke_close, candle_volume, candle_trades, candle_instrument_id, candle_timeframe)
FROM 'E:/Documents/Tecnici/Trading/HistoricalData/EURUSD_60.csv'
DELIMITER ','
CSV;

UPDATE candle SET candle_data = to_timestamp(candle_unix);

	