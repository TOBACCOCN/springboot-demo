package com.springboot.example.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InfluxDBTest {

	@Autowired
	private InfluxDBClient influxDBClient;

	@Test
	public void query () throws Exception {
		// String query = InfluxDBQuery.builder().bucket(InfluxDBConstant.INFLUXDB_BUCKET)
		// 		.rangeStart("-1w")
		// 		.filter("(r) => (r._measurement == \"http_api_request_duration_seconds\" and r.method == \"delete\")")
		// 		.build().getQuery();
		String query = InfluxDBQuery.builder().bucket(InfluxDBConstant.INFLUXDB_BUCKET)
				.rangeStart("-1w")
				.filter("(r) => (r._measurement == \"my_measurement\" and r.province == \"hb\" and r._field ==\"street\")")
				// .map("(r) => ({r with _time: uint(v: r._time)})")
				.build().getQuery();

		// 1. query raw
		String csv = influxDBClient.getQueryApi().queryRaw(query, InfluxDBConstant.INFLUXDB_ORG);
		log.info("csv: [{}]", csv);
		// asynchronous query
		influxDBClient.getQueryApi().queryRaw(query, InfluxDBConstant.INFLUXDB_ORG, (cancellable, line) -> log.info("line: [{}]", line));


		// 2. query with FluxTable list back
		List<FluxTable> tables = influxDBClient.getQueryApi().query(query, InfluxDBConstant.INFLUXDB_ORG);
		log.info("table size: [{}]", tables.size());
		tables.forEach(table -> table.getRecords().forEach(record -> log.info("VALUES: [{}]", record.getValues())));
		// asynchronous query
		influxDBClient.getQueryApi().query(query, InfluxDBConstant.INFLUXDB_ORG, (cancellable, fluxRecord) -> log.info("VALUES: [{}]", fluxRecord.getValues()));


		// 3. query with POJO list back
		// List<HttpApiRequestDurationSeconds> list = influxDBClient.getQueryApi().query(query, InfluxDBConstant.INFLUXDB_ORG, HttpApiRequestDurationSeconds.class);
		// log.info("list size: [{}]", list.size());
		// list.forEach(durationSeconds -> log.info("durationSeconds: [{}]", durationSeconds));
		// // asynchronous query
		// influxDBClient.getQueryApi().query(query, InfluxDBConstant.INFLUXDB_ORG, HttpApiRequestDurationSeconds.class, (cancellable, durationSeconds) -> log.info("durationSeconds: [{}]", durationSeconds));
		List<Address> list = influxDBClient.getQueryApi().query(query, InfluxDBConstant.INFLUXDB_ORG, Address.class);
		log.info("list size: [{}]", list.size());
		list.forEach(address -> log.info("address: [{}]", address));
	}

	@Test
	public void write() {
		// https://docs.influxdata.com/influxdb/v2/get-started/write/
		// String record = "http_api_request_duration_seconds,method=delete value=25.0";
		// influxDBClient.getWriteApiBlocking().writeRecord(InfluxDBConstant.INFLUXDB_BUCKET, InfluxDBConstant.INFLUXDB_ORG, WritePrecision.NS, record);
		// insert
		// String record = "my_measurement,province=hb,city=wh district=\"wc\",street=\"hpdd\",door_no=108";
		// influxDBClient.getWriteApiBlocking().writeRecord(InfluxDBConstant.INFLUXDB_BUCKET, InfluxDBConstant.INFLUXDB_ORG, WritePrecision.NS, record);
		// update
		String record = "my_measurement,province=hb,city=wh street=\"peace_avenue\" 1701745697097342000";
		influxDBClient.getWriteApiBlocking().writeRecord(InfluxDBConstant.INFLUXDB_BUCKET, InfluxDBConstant.INFLUXDB_ORG, WritePrecision.NS, record);
	}

	@Test
	public void delete() {
		OffsetDateTime start = OffsetDateTime.now().minusHours(1);
		OffsetDateTime stop = OffsetDateTime.now();
		// Delete predicate syntax
		// https://docs.influxdata.com/influxdb/v2/reference/syntax/delete-predicate/
		// influxDBClient.getDeleteApi().delete(start, stop, "_measurement=\"http_api_request_duration_seconds\" and method=\"delete\"", InfluxDBConstant.INFLUXDB_BUCKET, InfluxDBConstant.INFLUXDB_ORG);
		influxDBClient.getDeleteApi().delete(start, stop, "_measurement=\"my_measurement\"", InfluxDBConstant.INFLUXDB_BUCKET, InfluxDBConstant.INFLUXDB_ORG);
	}

}
