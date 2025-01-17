package com.pluralsight.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Ride> getRides() {
//		To fetch records, we are using jdbcTemplate's query methods  RowMapper (I) [Template method pattern]
		List <Ride> rides = jdbcTemplate.query("select * from ride", new RideRowMapper());
		return rides;
	}

	@Override
	public Ride createRide(Ride ride) {
//		jdbcTemplate.update("insert into ride (name, duration) values(?,?)",ride.getName(), ride.getDuration());
		
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
		
//		1. Define table columns as list of Columns
		List<String> columns = new ArrayList<>();
		columns.add("name");
		columns.add("duration");
		
//		2. Define Table Name and set defined columns (defined above)
		insert.setTableName("ride");
		insert.setColumnNames(columns);

//		3. Now put the Data in key value pairs
		Map<String,Object> data = new HashMap<>();
		data.put("name", ride.getName());
		data.put("duration", ride.getDuration());
		
//		4. Now insert into table
		// using Simple JDBC insert we can obtain the Primary key value returned when we insert into DB,
		// i.e., what we are defining below
		insert.setGeneratedKeyName("id");
		
		//insert into table nd get the id value back
		Number key = insert.executeAndReturnKey(data);
		
		System.out.println(key);
		
		return null;
	}
	
}
