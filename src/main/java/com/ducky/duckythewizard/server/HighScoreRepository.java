package com.ducky.duckythewizard.server;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface HighScoreRepository extends CrudRepository<HighScore, Integer> {

    List<HighScore> findAllByOrderByScoreDesc(Pageable pageable);
}