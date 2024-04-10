package com.springboot.example.influxdb;

import com.influxdb.annotations.Column;
import lombok.Data;

import java.time.Instant;

@Data
public class Address {
// [{result=_result, table=0, _start=2023-11-28T03:37:05.694654200Z, _stop=2023-12-05T03:37:05.694654200Z, _time=2023-12-05T03:08:17.097342Z, _value=wc, _field=district,
// 			_measurement=my_measurement, city=wh, province=hb}]
	@Column(tag = true)
	private String result;
	@Column(tag = true)
	private Integer table;
	@Column(timestamp = true)
	private Instant start;
	@Column(timestamp = true)
	private Instant stop;
	@Column(timestamp =  true)
	private Instant time;
	@Column
	private String value;
	// @Column
	// private Double field;
	@Column
	private String measurement;
	@Column(tag = true)
	private String province;
	@Column(tag = true)
	private String city;
}
