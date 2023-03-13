package com.green.nowon.service;

import org.springframework.ui.Model;

import com.green.nowon.domain.dto.board.AdminReplyUpdateDTO;
import com.green.nowon.domain.dto.board.BoardSaveDTO;
import com.green.nowon.domain.dto.board.BoardUpdateDTO;


public interface BoardService {

	void getListAll(int page, Model model);

	void sendDetail(long bno, Model model);

	void save(BoardSaveDTO dto, String name);

	void save(BoardSaveDTO dto);

	void delete(long bno);

	void update(long bno, BoardUpdateDTO dto);

	void updateProc(long bno, BoardUpdateDTO dto);
	
	String updateAdmin(long bno, AdminReplyUpdateDTO dto);

	void getAdminQnaList(int page,int replyNum, Model model);
	
	void getBeforeAdminQnaList(int page,int replyNum, Model model);
	
	void getAdminQnaCheck(long bno, Model model);
	
	
}
