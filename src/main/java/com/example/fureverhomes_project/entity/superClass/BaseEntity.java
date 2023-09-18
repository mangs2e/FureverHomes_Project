package com.example.fureverhomes_project.entity.superClass;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "UPLOAD_DATE", nullable = false)
    private LocalDate createdTime; //생성일

    @Column(name = "MODI_DATE")
    private LocalDate modifiedTime; //수정일
}
