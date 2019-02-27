package com.kh.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.spring.board.model.vo.Attachment;

public interface BoardDao {

	List<Map<String,String>> selectBoardList(int cPage, int numPerPage);
	int selectBoardCount();
	int insertBoard(Map<String,String> board);
	int insertAttach(Attachment a);
	Map<String, String> selectBoard(int boardNo);
	List<Map<String, String>> selectAttach(int boardNo);
}







