package com.example.fureverhomes_project.repository;

import com.example.fureverhomes_project.entity.Adopt;
import com.example.fureverhomes_project.entity.Animal;
import com.example.fureverhomes_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptRepository extends JpaRepository<Adopt, Long> {
    Boolean existsByAnimalAndMember(Animal animal, Member member);
}
