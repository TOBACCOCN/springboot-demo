package com.springboot.example.influxdb;

import lombok.Builder;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Builder
public class InfluxDBQuery {
	private String bucket;
	private String rangeStart;
	private String rangeStop;
	private String filter;
	private String map;
	@Setter
	private String query;

	public String getQuery() throws Exception {
		if (StringUtils.isBlank(bucket)) {
			throw new Exception("bucket could not be blank");
		}
		if (StringUtils.isBlank(rangeStart) && StringUtils.isNotBlank(rangeStop)) {
			throw new Exception("rangeStart could not be blank when rangeStop is not blank");
		}

		StringBuilder builder = new StringBuilder("from(bucket: \""+ bucket + "\")");
		boolean rangeStopNotBlank = StringUtils.isNotBlank(rangeStop);
		if (StringUtils.isNotBlank(rangeStart)) {
			builder.append("|> range(start: ").append(rangeStart).append(rangeStopNotBlank ? ", stop: " + rangeStop : "").append(")");
		}
		if (StringUtils.isNotBlank(filter)) {
			builder.append("|> filter(fn: ").append(filter).append(")");
		}
		if (StringUtils.isNotBlank(map)) {
			builder.append("|> map(fn: ").append(map).append(")");
		}
		return builder.toString();
	}

}
