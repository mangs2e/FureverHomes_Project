package com.example.fureverhomes_project.controller;

import com.example.fureverhomes_project.dto.MailDTO;
import com.example.fureverhomes_project.dto.MemberReqDto;
import com.example.fureverhomes_project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fureverhomes")
public class MemberController {

    private final MemberService memberService;

    //로그인 회원가입을 위한 메인 페이지
    @GetMapping("/main")
    public String mainPage() {
        return "html/furever_main";
    }

    //로그인 페이지
    @GetMapping("/signin")
    public String signInPage() {
        return "html/furever_sign-in";
    }

    //회원가입 페이지
    @GetMapping("/signup")
    public String signUpPage() {
        return "html/furever_sign-up";
    }

    //이메일 인증 페이지
    @GetMapping("/signup/emailAuth")
    public String emailAuthPage() {
        return "html/furever_Email-Auth";
    }

    //비밀번호 찾기 페이지
    @GetMapping("/signin/findPassword")
    public String findPasswordPage() {
        return "html/furever_password-find";
    }

    //비밀번호 재설정 페이지
    @GetMapping("/signin/changePassword")
    public String changePasswordPage(@RequestParam String linkId) {
        Long expirationTime = memberService.getLinkExpirationMap(linkId); // 링크별 만료 시간 조회
        if (expirationTime == null || System.currentTimeMillis() > expirationTime) {
            // 만료된 링크 처리
            throw new IllegalStateException("만료된 링크입니다.");
        }
        return "html/furever_password-change";
    }


    @RestController
    @RequestMapping("/fureverhomes")
    public class MemberRestController{

        //회원가입 정보 전송
        @PostMapping("/signup.post")
        public ResponseEntity<String> signUpMember(@RequestBody MemberReqDto memberReqDto) {
            if (memberService.saveMember(memberReqDto) != null) {
                System.out.println(ResponseEntity.ok(memberReqDto.getEmail()));
                return ResponseEntity.ok(memberReqDto.getEmail());
            }
            return ResponseEntity.internalServerError().build();
        }

        //이메일 인증
        @PostMapping("/signup/emailAuth.post")
        public ResponseEntity<Object> emailAuth(@RequestBody MailDTO mailDTO) {
            if (memberService.sendEmailWithAuthCode(mailDTO)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        }

        //인증코드 여부 유효성 검사
        @PostMapping("/signup/success.post")
        public ResponseEntity<Object> successEmailAuth(@RequestBody Map<String,String> mapParam) {
            if(memberService.isSuccessAuth(mapParam)){
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        //로그인
        @PostMapping("/signin.post")
        public ResponseEntity<Object> signin(@RequestBody Map<String, String> params, HttpServletRequest request) {

            //1. 회원 정보 조회
            Long loginMember = memberService.singin(params);

            //2. 세션에 회원 정보 저장 & 만료 시간 설정
            if (loginMember != null) {
                HttpSession session = request.getSession();
                session.setAttribute("longinMember", loginMember);
                session.setMaxInactiveInterval(60 * 30); //30분
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        //로그아웃
        @PostMapping("/signout.post")
        public ResponseEntity<Object> signout(HttpServletRequest request) {
            HttpSession session = request.getSession();
            if (session != null) {
                session.invalidate();
            }
            return ResponseEntity.ok().build();
        }

        //비밀번호 재설정 링크 전송
        @PostMapping("/signin/sendLink.post")
        public ResponseEntity<Object> sendResetPwdLink(@RequestBody MailDTO mailDTO, HttpServletRequest request) throws MessagingException {
            if (memberService.sendLinkMail(mailDTO)) {
                HttpSession session = request.getSession();
                session.setAttribute("email", mailDTO.getEmail());
                return ResponseEntity.ok().build();
            } else if (!memberService.sendLinkMail(mailDTO)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        @PostMapping("/signin/change.post")
        public ResponseEntity<Object> changePassword(@RequestBody Map<String,String> mapParam, HttpServletRequest request) {
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
            mapParam.put("email", email);
            session.removeAttribute("email");
            if (memberService.changePassword(mapParam)) {
                return ResponseEntity.ok().build();
            } return ResponseEntity.internalServerError().build();
        }
    }
}
