package com.korenik.train.repository;

import com.korenik.train.entity.Square;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquareRepository extends JpaRepository<Square, Long> {
}
