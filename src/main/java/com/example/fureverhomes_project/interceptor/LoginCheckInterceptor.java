package com.example.fureverhomes_project.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//세션에 로그인 정보가 있는지를 확인하기 위한 인터셉터
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.세션에서 회원 정보 조회
        HttpSession session = request.getSession();
        Long member = (Long) session.getAttribute("loginMember");

        //2. 만료시 로그인 페이지로 이동
        if (member == null) {
            response.sendRedirect("/fureverhomes/signin");
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
