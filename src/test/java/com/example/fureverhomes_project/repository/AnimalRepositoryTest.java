package com.example.fureverhomes_project.repository;

import com.example.fureverhomes_project.entity.Animal;
import com.example.fureverhomes_project.entity.QAnimal;
import com.example.fureverhomes_project.entity.enumClass.Sex;
import com.example.fureverhomes_project.entity.enumClass.Species;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnimalRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    AnimalRepository animalRepository;

    @Test
    @DisplayName("동물 정보 등록")
    public void saveAnimalTest() {
        Animal animal = new Animal();
        animal.setName("animal");
        animal.setSpecies(Species.CAT);
        animal.setSex(Sex.M);
        animal.setNueter(true);
        animal.setPicture("1.jpg");
        animal.setShelter_name("shelter");
        Animal saveAnimal = animalRepository.save(animal);
        System.out.println(saveAnimal.toString());
    }

    public void saveAnimalListTest() {
        for (int i = 1; i <= 10; i++) {
            Animal animal = new Animal();
            animal.setName("animal"+i);
            animal.setSpecies(Species.CAT);
            animal.setSex(Sex.M);
            animal.setNueter(true);
            animal.setPicture(i+".jpg");
            animal.setShelter_name("shelter"+i);
            Animal saveAnimal = animalRepository.save(animal);
            System.out.println(saveAnimal.toString());
        }
    }

    @Test
    @DisplayName("동물 리스트 조회")
    public void findAllTest() {
        this.saveAnimalListTest();
        List<Animal> animals = animalRepository.findAll();
        for(Animal animal : animals) {
            System.out.println(animal.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트")
    public void queryDslTest() {
        this.saveAnimalListTest();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QAnimal qAnimal = QAnimal.animal;
        JPAQuery<Animal> query = queryFactory.selectFrom(qAnimal)
                .where(qAnimal.species.eq(Species.CAT))
                .where(qAnimal.sex.eq(Sex.M))
                .orderBy(qAnimal.name.asc());

        List<Animal> animals = query.fetch(); //조회 결과 리스트 반환

        for(Animal a:animals) {
            System.out.println(a.toString());
        }
    }

}