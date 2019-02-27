package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.common.exception.BoardException;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDao dao;

	@Override
	/*@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)*/
	/*@Transactional*/
	public int insertBoard(Map<String, String> board, List<Attachment> files) throws BoardException {
		// dao세번가야함.
		int result = 0;
		int boardNo = 0;

		try {
			result = dao.insertBoard(board);
			System.out.println("돌아온값 : " + board.get("boardNo"));

			if (result == 0) {
				throw new BoardException("게시판 등록 실패");
			}
			for (Attachment a : files) {
				a.setBoardNo(Integer.parseInt(board.get("boardNo")));
				result = dao.insertAttach(a);
				
				if (result == 0)
					throw new BoardException("파일업로드 실패");
			}

		} 
		catch (Exception e) {
			/* throw new RuntimeException(e.getMessage()); */
			/* throw new BoardException("업로드 실패!"); */
			throw e;

		}

		return result;
	}

	@Override
	public List<Map<String, String>> selectBoardList(int cPage, int numPerPage) {
		return dao.selectBoardList(cPage, numPerPage);
	}

	@Override
	public int selectBoardCount() {
		return dao.selectBoardCount();
	}

	@Override
	public Map<String, String> selectBoard(int boardNo) {
		return dao.selectBoard(boardNo);
	}

	@Override
	public List<Map<String, String>> selectAttach(int boardNo) {
		return dao.selectAttach(boardNo);
	}
}
