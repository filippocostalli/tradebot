CREATE TABLE instrument(
  instrument_id integer NOT NULL,
  instrument_name TEXT NOT NULL,
  PRIMARY KEY (instrument_id),
  UNIQUE (instrument_name)
);
  

CREATE TABLE tick (
	tick_id            serial NOT NULL,
	tick_instrument_id integer NOT NULL,
	tick_timestamp     timestamp NOT NULL,
 	tick_bid_price     NUMERIC NOT NULL,
 	tick_ask_price     NUMERIC NOT NULL,
 	PRIMARY KEY (tick_id),
 	CONSTRAINT tick_instrument_fk FOREIGN KEY(tick_instrument_id) REFERENCES instrument(instrument_id)
);


/*
Open - The first traded price
High - The highest traded price
Low - The lowest traded price
Close - The final traded price
Volume - The total volume traded by all trades
Trades - The number of individual trades

*/
CREATE TABLE candle (
	candle_id     SERIAL NOT NULL,
	candle_instrument_id integer NOT NULL,
	candle_unix   abstime NOT NULL,
	candle_data   timestamp without time zone NOT NULL default '1970-01-01',
	candle_market VARCHAR(10) DEFAULT NULL, -- ??????
	candle_open   NUMERIC NOT NULL,
	candke_close  NUMERIC NOT NULL,
	candle_min    NUMERIC NOT NULL,
	candle_max    NUMERIC NOT NULL,
	candle_volume NUMERIC,
	candle_trades INTEGER,
	candle_timeframe INTEGER NOT NULL, -- 1, 5, 15, 60, 720 and 1440 minute intervals,
	PRIMARY KEY (candle_id),
	CONSTRAINT candle_instrument_fk FOREIGN KEY(candle_instrument_id) REFERENCES instrument(instrument_id)
);


