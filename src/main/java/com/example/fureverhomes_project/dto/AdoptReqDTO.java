package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.Adopt;
import com.example.fureverhomes_project.entity.Animal;
import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.entity.enumClass.AdoptStatus;
import lombok.Getter;

@Getter
public class AdoptReqDTO {
    private Long memberId;
    private Long animalId;
    private String phonenum;
    private String contact_time;
    private String residence;
    private String job;
    private Boolean breeding;
    private String adopt_reason;
    private String add_comment;
    private AdoptStatus adopt_status;

    public Adopt toEntity(Member member, Animal animal) {
        return Adopt.builder()
                .member(member)
                .animal(animal)
                .phonenum(phonenum)
                .contact_time(contact_time)
                .residence(residence)
                .job(job)
                .breeding(breeding)
                .adopt_reason(adopt_reason)
                .add_comment(add_comment)
                .adopt_status(AdoptStatus.PROCEEDING)
                .build();
    }

    public void updateMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
