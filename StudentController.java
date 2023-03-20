package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController
{
	@Autowired
	JdbcTemplate jtemp;
	
	
	@RequestMapping(value="/student/{rollno}",method=RequestMethod.DELETE)
	public ResponseEntity<Object>delete(@PathVariable("rollno")String rollno)
	{
        jtemp.update("delete from student where rollno=?",rollno);
         return new ResponseEntity<>("Student is deleted succesfully",HttpStatus.OK);
}
	@RequestMapping(value="/student/{rollno}",method=RequestMethod.PUT)
	public ResponseEntity<Object>UpdateStudent(@PathVariable("rollno")String rollno,@RequestBody Student student)
	{
		jtemp.update("update student set name=?,address=?,marks=? where rollno=?",student.getName(),student.getAddress(),student.getMarks(),rollno);
		return new ResponseEntity<>("Student is updated succesfully",HttpStatus.OK);
	}
	
	@RequestMapping(value="/student",method=RequestMethod.POST)
	public ResponseEntity<Object>CreateStudent(@RequestBody Student student)
	{
		jtemp.update("insert into student values(?,?,?,?)",student.getRollno(),student.getName(),student.getAddress(),student.getMarks());
		return new ResponseEntity<> ("student is inserted succesfully",HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/student")
	public List<Student> getstudent()
	{
		List<Student> l=new ArrayList<>();
		
		jtemp.query("select * from student", new RowMapper()
		{

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
			{
				Student s1=new Student();
				s1.setRollno(rs.getString(1));
				s1.setName(rs.getString(2));
				s1.setAddress(rs.getString(3));
				s1.setMarks(rs.getInt(4));
				l.add(s1);
				return s1;
			
				
			}
			
		});
		return l;
		
	}
	}