package com.example.fureverhomes_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {
    private String email; //이메일 주소
    private String title; //제목
    private String message; //메시지 내용
    private int authCode; //인증코드
}
