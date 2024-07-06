package com.quizapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizapp.model.Question;
import com.quizapp.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {

	@Autowired
	QuestionService service;

	@GetMapping("allQuestions")
	public ResponseEntity<List<Question>> allQuestions() {
		try {
			return new ResponseEntity<List<Question>>(service.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Question>>(new ArrayList<Question>(), HttpStatus.EXPECTATION_FAILED);
	}

	@GetMapping("category/{category}")
	public ResponseEntity<List<Question>> questionsByCategory(@PathVariable String category) {
		try {
			return new ResponseEntity<List<Question>>(service.findByCategory(category), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Question>>(new ArrayList<Question>(), HttpStatus.BAD_REQUEST);
	}

	@PostMapping("add")
	public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
		try {
			return new ResponseEntity<Question>(service.save(question),HttpStatus.CREATED);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Question>(new Question(), HttpStatus.EXPECTATION_FAILED);
		
	}
}
