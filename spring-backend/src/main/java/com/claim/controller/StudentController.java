package com.claim.controller;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import com.claim.entity.Student;
import com.claim.repository.StudentRepository;

@RestController
@CrossOrigin
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@EnableWebMvc
	@ComponentScan("org.springframework.security.samples.mvc")
	public class WebMvcConfiguration {
		
		public void addviewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/login").setViewName("login");
			registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		}
	}	
	
	
	@RequestMapping(value="/submitStudentDetails",
			consumes =MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE,
			method =RequestMethod.POST)
	public void submitStudentDetails(@RequestBody Student student) {
		this.studentRepository.save(student);
	}
		
		@RequestMapping(value="/login",
			produces =MediaType.APPLICATION_JSON_VALUE,
			consumes =MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
		@ResponseBody
		public ResponseEntity<Optional<Student>> login (@RequestBody Student student){
			Optional<Student> s = this.studentRepository.findById(student.getEmail());
			if(s.isPresent()) {
				Student temp = s.get();
				if(temp.getPassword().equals(student.getPassword())){
					return new ResponseEntity<Optional<Student>>(s, HttpStatus.OK);
				}
			}
			return new ResponseEntity<Optional<Student>>(HttpStatus.UNAUTHORIZED);
		}
			
	
	
		@RequestMapping(value="/findStudentById",
			produces=MediaType.APPLICATION_JSON_VALUE,
			method= RequestMethod.GET)
		    
		@ResponseBody
			public ResponseEntity<Optional<Student>> findByEmail(String email) {
			Optional<Student> student = this.studentRepository.findById(email);
			return new ResponseEntity<Optional<Student>> (student, HttpStatus.OK);
		}
		
	//import list from java util
		@RequestMapping(value="/findAll",
				produces =MediaType.APPLICATION_JSON_VALUE,
				method =RequestMethod.GET)
		@ResponseBody
		public ResponseEntity<List<Student>> findAll(){
			List<Student> student =this.studentRepository.findAll();
			return new ResponseEntity<List<Student>>(student, HttpStatus.OK);
		}

		
	}


