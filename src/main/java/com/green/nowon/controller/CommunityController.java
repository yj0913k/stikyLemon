package com.green.nowon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommunityController {

    //커뮤니티 페이지
    @GetMapping("/comm/qna")
    public String admin() {
        return "community/qna";
    }

    //공지사항 페이지
    @GetMapping("/comm/notice")
    public String notice() {
        return "community/notice";
    }

    //자주묻는질문 페이지
    @GetMapping("/comm/faq")
    public String faq() {
        return "community/faq";
    }

    //리뷰 페이지
    @GetMapping("/comm/review")
    public String review() {
        return "community/review";
    }

    //리뷰작성 페이지
    @GetMapping("/members/review-write")
    public String reviewWrite() {
        return "community/review-write";
    }

    //이벤트 페이지
    @GetMapping("/comm/event")
    public String event() {
        return "community/event";
    }
}
