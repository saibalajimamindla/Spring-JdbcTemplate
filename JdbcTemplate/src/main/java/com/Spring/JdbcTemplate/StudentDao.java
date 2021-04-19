package com.Spring.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class StudentDao {
	private JdbcTemplate jdbcTemplate;

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public StudentDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public StudentDao() {

	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

// CRUD using PreparedStatement
	// Method to save the student
	public Boolean saveStudentByPreparedStatement(final Student s) {
		String query = "insert into student values(?,?,?)";
		return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

				ps.setInt(1, s.getId());
				ps.setString(2, s.getName());
				ps.setString(3, s.getDepartment());

				return ps.execute();

			}
		});
	}

	// Method to update the Student
	public Boolean updateStudentByPreparedStatement(final Student s) {
		String query = "update student set name=?,department=? where id =?";
		return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

				ps.setString(1, s.getName());
				ps.setString(2, s.getDepartment());
				ps.setInt(3, s.getId());

				return ps.execute();

			}
		});
	}

	// Method to delete the Student
	public Boolean deleteStudentByPreparedStatement(final int id) {
		String query = "delete from student where id =?";
		return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

				ps.setInt(1, id);

				return ps.execute();

			}
		});
	}

//  Reading data by using ResultSetExtractor
	public List<Student> getAllStudentsResultSetExtractor() {
		return jdbcTemplate.query("select * from student", new ResultSetExtractor<List<Student>>() {

			public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<Student> list = new ArrayList<Student>();
				while (rs.next()) {
					Student s = new Student();
					s.setId(rs.getInt(1));
					s.setName(rs.getString(2));
					s.setDepartment(rs.getString(3));
					list.add(s);
				}
				return list;
			}
		});
	}

//  Reading data by using RowMapper
	public List<Student> getAllStudentsRowMapper() {
		return jdbcTemplate.query("select * from student", new RowMapper<Student>() {
			public Student mapRow(ResultSet rs, int rownumber) throws SQLException {
				Student e = new Student();
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setDepartment(rs.getString(3));
				return e;
			}
		});
	}

// CRUD using NamedParameterJdbcTemplate
	// Method to save the Student
	public void saveStudentBynamedParameterJdbcTemplate(Student s) {
		String query = "insert into student values (:id,:name,:department)";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", s.getId());
		map.put("name", s.getName());
		map.put("department", s.getDepartment());

		namedParameterJdbcTemplate.execute(query, map, new PreparedStatementCallback<Object>() {

			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate();
			}
		});
	}

// Method to update the Student
	public void updateStudentBynamedParameterJdbcTemplate(Student s) {
		String query = "update student set name=:name,department=:department where id =:id";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", s.getName());
		map.put("department", s.getDepartment());
		map.put("id", s.getId());

		namedParameterJdbcTemplate.execute(query, map, new PreparedStatementCallback<Object>() {

			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate();
			}
		});
	}

//Method to delete the Student
	public void deleteStudentBynamedParameterJdbcTemplate(int id) {
		String query = "delete from student where id =:id";

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);

		namedParameterJdbcTemplate.execute(query, map, new PreparedStatementCallback<Object>() {

			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate();
			}
		});
	}

}
