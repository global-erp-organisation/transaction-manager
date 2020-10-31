package com.ia.transaction.repository;

import com.ia.transaction.view.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<TransactionCategory, String> {
    Optional<TransactionCategory> findByCategoryDescription(String description);
}
