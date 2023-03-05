package com.venuprasath.jdbc;

import com.venuprasath.jdbc.dataaccess.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class JdbcApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(JdbcApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
	}

	@Autowired
	JdbcTemplate template;

	@Override
	public void run(String... strings) throws Exception {
		logger.info("Creating tables");

		template.execute("DROP TABLE customers IF EXISTS");
		template.execute("CREATE TABLE customers(" +
				"id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

		List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
				.map(name -> name.split(" "))
				.collect(Collectors.toList());

		splitUpNames.forEach( name -> logger.info(String.format("Inserting customer record for %s %s", name[0], name[1])));
		template.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?, ?)", splitUpNames);

		logger.info("Querying for customer records where first_name = 'Josh':");
		template.query("SELECT id, first_name, last_name FROM customers WHERE first_name=?",
				new Object[] { "Josh"},
				(rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"),
						rs.getString("last_name"))).forEach( customer ->
				logger.info(customer.toString()));
	}

}
