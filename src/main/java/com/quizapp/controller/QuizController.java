package com.quizapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quizapp.model.Question;
import com.quizapp.model.QuestionWrapper;
import com.quizapp.model.Quiz;
import com.quizapp.model.Response;
import com.quizapp.service.QuestionService;
import com.quizapp.service.QuizService;

@RestController
@RequestMapping("quiz")
public class QuizController {

	@Autowired
	QuizService quiz_service;

	@Autowired
	QuestionService question_service;

	@PostMapping("create")
	public ResponseEntity<Quiz> createQuiz(@RequestParam String category, @RequestParam int numQ,
			@RequestParam String title) {

		try {
			List<Question> questions = question_service.findRandomQuestionsByCategory(category, numQ);
			Quiz quiz = new Quiz();
			quiz.setQuestions(questions);
			quiz.setTitle(title);

			return new ResponseEntity<Quiz>(quiz_service.save(quiz), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Quiz>(new Quiz(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id) {

		try {
			List<Question> questions = quiz_service.findById(id).get().getQuestions();

			List<QuestionWrapper> qw = new ArrayList<QuestionWrapper>();

			for (Question question : questions) {
				qw.add(new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(),
						question.getOption2(), question.getOption3(), question.getOption4()));
			}

			return new ResponseEntity<List<QuestionWrapper>>(qw, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<QuestionWrapper>>(new ArrayList(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("submit/{id}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
		try {
			List<Question> questions = quiz_service.findById(id).get().getQuestions();
			int score = 0, i = 0;
			
			for (Response response : responses) {
				if (response.getResponse().equals(questions.get(i).getRightAnswer()))
					score++;
				i++;
			}
			return new ResponseEntity<Integer>(score, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
		}
	}

}
