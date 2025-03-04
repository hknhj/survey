package com.example.survey.domain.option.repository;

import com.example.survey.domain.option.domain.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {

}
