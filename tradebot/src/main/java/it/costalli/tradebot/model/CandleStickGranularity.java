package it.costalli.tradebot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CandleStickGranularity {

	S5(5, "5 seconds"), // 5s
	S10(10, "10 seconds"), // 10s
	S15(15, "15 seconds"), // 15s
	S30(30, "30 seconds"), // 30s
	M1(60 * 1, "1 minute"), // 1min
	M2(60 * 2, "2 minutes"), // 2mins
	M3(60 * 3, "3 minutes"), // 3mins
	M5(60 * 5, "5 minutes"), // 5mins
	M10(60 * 10, "10 minutes"), // 10mins
	M15(60 * 15, "15 minutes"), // 15mins
	M30(60 * 30, "30 minutes"), // 30mins
	H1(60 * 60, "1 hour"), // 1hr
	H2(60 * 60 * 2, "2 hours"), // 2hr
	H3(60 * 60 * 3, "3 hours"), // 3hr
	H4(60 * 60 * 4, "4 hours"), // 4hr
	H6(60 * 60 * 6, "6 hours"), // 6hr
	H8(60 * 60 * 8, "8 hours"), // 8hr
	H12(60 * 60 * 12, "12 hours"), // 12hr
	D(60 * 60 * 24, "1 day"), // 1day
	W(60 * 60 * 24 * 7, "1 week"), // 1wk
	M(60 * 60 * 24 * 30, "1 month");// 1mth

	private final long granularityInSeconds;
	private final String label;

}