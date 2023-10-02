package com.example.fureverhomes_project.entity;

import com.example.fureverhomes_project.entity.enumClass.Sex;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "interest_animal")
@Entity
@Getter
@NoArgsConstructor
public class Interest {

    @Id @Column(name = "INTEREST_ID")
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ANIMAL_ID")
    private Animal animal;

    @Builder
    public Interest(Member member, Animal animal) {
        this.member = member;
        this.animal = animal;
    }

}
