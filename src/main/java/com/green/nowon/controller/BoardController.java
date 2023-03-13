package com.green.nowon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.nowon.domain.dto.board.BoardSaveDTO;
import com.green.nowon.domain.dto.board.BoardUpdateDTO;
import com.green.nowon.security.MyUserDetails;
import com.green.nowon.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService service;
	
	//고객센터 페이지 
	 @GetMapping("/comm/board-list")
	 public String board(@RequestParam(defaultValue = "1") int page , Model model) {//문자열로 파라미터 매핑--> int형 parse
			service.getListAll(page ,model);
	        return "community/board/board-list";
	    }
	//상세페이지 조회
	@GetMapping("/comm/board-list/{bno}")
	public String detail(@PathVariable long bno, Model model) {
		service.sendDetail(bno, model);
		return "community/board/detail";
	}
	 
    //문의작성 페이지
	 @GetMapping("/members/board-write")
	    public String boardWrite() {
	        return "community/board/board-write";
	    }
	 
	 
	 @PostMapping("/comm/board-list")            //   Principal principal 객체도 가능 username 정보만확인가능
		public String write(BoardSaveDTO dto, Authentication auth) {
			MyUserDetails myUserDetails=(MyUserDetails)auth.getPrincipal();
			dto.setMno(myUserDetails.getMno());
			service.save(dto);
			return "redirect:/comm/board-list";
		}
	//삭제
		@DeleteMapping("/comm/board-list/{bno}")
		public String delete(@PathVariable long bno) {
			service.delete(bno);
			return "redirect:/comm/board-list";
		}
		//수정
		@PutMapping("/comm/board-list/{bno}")                 //setter 있어야함.
		public String update(@PathVariable long bno, BoardUpdateDTO dto) {
			
			//System.out.println(">>>>>>>>>>"+bno +"수정처리:"+ dto);
			//service.update(bno, dto);
			
			service.updateProc(bno, dto);
			
			return "redirect:/comm/board-list/{bno}";
		}
	 
	 
	
}
