package com.example.fureverhomes_project.repository;

import com.example.fureverhomes_project.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
