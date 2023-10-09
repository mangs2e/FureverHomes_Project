package com.example.fureverhomes_project.repository;

import com.example.fureverhomes_project.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//동물 관련 작업을 위한 레파지토리
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>{
}
