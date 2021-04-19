package com.Spring.JdbcTemplate;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		// CRUD using PreparedStatement
		StudentDao dao = (StudentDao) context.getBean("sdao");
		dao.saveStudentByPreparedStatement(new Student(100, "Amit", "cse"));
		dao.saveStudentByPreparedStatement(new Student(101, "kumar", "mech"));
		dao.saveStudentByPreparedStatement(new Student(102, "kumar", "eee"));
		dao.updateStudentByPreparedStatement(new Student(100, "vijay", "cse"));
		dao.deleteStudentByPreparedStatement(102);

		// CRUD using NamedParameterJdbcTemplate
		StudentDao dao1 = (StudentDao) context.getBean("sdaonamed");
		dao1.saveStudentBynamedParameterJdbcTemplate(new Student(200, "raveendra", "cse"));
		dao1.saveStudentBynamedParameterJdbcTemplate(new Student(201, "balaji", "ece"));
		dao1.saveStudentBynamedParameterJdbcTemplate(new Student(203, "vishnu", "eee"));
		dao1.updateStudentBynamedParameterJdbcTemplate(new Student(201, "balaji", "mech"));
		dao1.deleteStudentBynamedParameterJdbcTemplate(200);

		// Reading data by using ResultSetExtractor
		List<Student> listResultSetExtractor = dao.getAllStudentsResultSetExtractor();
		System.out.println("printing using ResultSetExtractor");
		for (Student e : listResultSetExtractor)
			System.out.println(e);

		// Reading data by using RowMapper
		List<Student> listRowMapper = dao.getAllStudentsRowMapper();
		System.out.println("printing using rowmapper");
		for (Student e : listRowMapper)
			System.out.println(e);
	}
}