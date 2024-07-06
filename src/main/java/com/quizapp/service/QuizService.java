package com.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import com.quizapp.model.Quiz;

@Service
public interface QuizService extends JpaRepository<Quiz, Integer>{

}
