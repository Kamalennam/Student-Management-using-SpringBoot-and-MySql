package com.example.demo.controller;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;




@RestController
public class StudentController {
	
	@Configuration
	public class CorsConfig implements WebMvcConfigurer {
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("http://localhost:4200") // Allow requests from this origin
	                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow only specific methods
	                .allowCredentials(true) // Allow sending cookies
	                .maxAge(3600); // Max age of the preflight request in seconds
	    }
	}
	
	@Autowired
 	StudentRepository studentrepository;

	@PostMapping("/students")
	public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
		
		return new ResponseEntity<>(studentrepository.save(student),HttpStatus.CREATED);
		 
	}
	
	@GetMapping("/findAllStudents")
	public ResponseEntity<List<Student>> getAllStudents( Student student) {
		System.out.println("executed..");
		return new ResponseEntity<>(studentrepository.findAll(),HttpStatus.OK);
		 
	}
//	@GetMapping("/findAllStudents")
//	public String getAllStudents( ) {
////		return new ResponseEntity<>(studentrepository.findAll(),HttpStatus.OK);
//		return "Hello World";
//		 
//	}
	
	@GetMapping("/findAllStudents/{id}")
	public ResponseEntity<Student> getStudentBasedOnId(@PathVariable long id) {
		 Optional<Student> student = studentrepository.findById(id);
		 if(student.isPresent()) {
			 return new ResponseEntity<>(student.get(), HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	
	@PutMapping("/findAllStudents/{id}")
	public ResponseEntity<Student> updateStudentBasedOnId(@PathVariable long id, @RequestBody Student stdnt) {
		 Optional<Student> student = studentrepository.findById(id);
		 if(student.isPresent()) {
			 student.get().setAddress(stdnt.getAddress());
			 student.get().setEmail(stdnt.getEmail());
			 student.get().setName(stdnt.getName());
			 return new ResponseEntity<>(studentrepository.save(student.get()), HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	
	@DeleteMapping("/findAllStudents/{id}")
	public ResponseEntity<Void> deleteStudentBasedOnId(@PathVariable long id) {
		 Optional<Student> student = studentrepository.findById(id);
		 if(student.isPresent()) {
			 studentrepository.deleteById(id);
			 return new ResponseEntity<>( HttpStatus.NO_CONTENT);
		 }
		 else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	
}
