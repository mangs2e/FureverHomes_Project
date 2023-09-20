package com.example.fureverhomes_project.controller;

import com.example.fureverhomes_project.dto.MailDTO;
import com.example.fureverhomes_project.dto.MemberReqDto;
import com.example.fureverhomes_project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fureverhomes")
public class MemberController {

    private final MemberService memberService;

    //로그인 회원가입을 위한 메인 페이지
    @GetMapping
    public String getMain() {
        return "html/furever_main";
    }

    //로그인 페이지
    @GetMapping("/signin")
    public String getSignIn() {
        return "html/furever_sign-in";
    }

    //회원가입 페이지
    @GetMapping("/signup")
    public String getSignUp() {
        return "html/furever_sign-up";
    }

    //이메일 인증 페이지
    @GetMapping("/signup/emailAuth")
    public String getEmailAuth() {
        return "html/furever_Email-Auth";
    }



    @RestController
    @RequestMapping("/fureverhomes")
    public class MemberRestController{

        //회원가입 정보 전송
        @PostMapping("/signup.post")
        public ResponseEntity<String> signUpMember(@RequestBody MemberReqDto memberReqDto, RedirectAttributes redirectAttributes) {
            if (memberService.saveMember(memberReqDto) != null) {
                System.out.println(ResponseEntity.ok(memberReqDto.getEmail()));
                return ResponseEntity.ok(memberReqDto.getEmail());
            }
            return ResponseEntity.internalServerError().build();
        }

        //이메일 인증
        @PostMapping("/signup/emailAuth.post")
        public ResponseEntity<Void> emailAuth(@RequestBody MailDTO mailDTO) {
            if (memberService.sendEmailWithAuthCode(mailDTO)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        }

        //인증코드 여부 유효성 검사
        @PostMapping("/signup/success.post")
        public ResponseEntity<Void> successEmailAuth(@RequestBody Map<String,String> mapParam) {
            if(memberService.isSuccessAuth(mapParam)){
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
    }
}
