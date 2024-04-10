package com.springboot.example.influxdb;

import com.influxdb.annotations.Column;
import lombok.Data;

import java.time.Instant;

@Data
public class HttpApiRequestDurationSeconds {
// [{result=_result, table=0, _start=2023-11-29T10:40:35.964308900Z, _stop=2023-11-29T10:41:35.964308900Z, _time=2023-11-29T10:40:44.779993300Z, _value=41.0,
// 			_field=+Inf, _measurement=http_api_request_duration_seconds, handler=platform, method=GET, path=/:file_name.js, response_code=200, status=2XX, user_agent=Firefox}]
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
	private Double value;
	// @Column
	// private Double field;
	@Column
	private String measurement;
	@Column(tag = true)
	private String handler;
	@Column(tag = true)
	private String method;
	@Column(tag = true)
	private String path;
	@Column(tag = true)
	private String responseCode;
	@Column(tag = true)
	private String status;
	@Column(tag = true)
	private String userAgent;

}
