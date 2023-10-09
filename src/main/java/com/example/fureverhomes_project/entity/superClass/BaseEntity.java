package com.example.fureverhomes_project.entity.superClass;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "UPLOAD_DATE")
    private LocalDateTime createdTime = LocalDateTime.now(); //생성일

    @Column(name = "MODI_DATE")
    private LocalDateTime modifiedTime; //수정일

    public LocalDateTime getUploadDate() {
        return this.createdTime;
    }
}
