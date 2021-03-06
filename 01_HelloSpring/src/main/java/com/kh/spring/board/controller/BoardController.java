package com.kh.spring.board.controller;

import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.common.PageFactory;
import com.kh.spring.common.exception.BoardException;

@Controller
public class BoardController {
	private Logger logger=LoggerFactory.getLogger(BoardController.class);
	@Autowired
	BoardService service;
	
	
	@RequestMapping("/board/boardList")
	public ModelAndView boardList(
			@RequestParam(value="cPage", 
			required=false, defaultValue="0") int cPage
			)
	{
		int numPerPage=5;
		ModelAndView mv=new ModelAndView();
		int contentCount=service.selectBoardCount();
		List<Map<String,String>> list=service.selectBoardList(cPage,numPerPage);
		mv.addObject("pageBar",PageFactory.getPageBar(contentCount, cPage, numPerPage, "/spring/board/boardList"));
		mv.addObject("list",list);
		mv.setViewName("board/boardList");
		return mv;
	}
	
	@RequestMapping("/board/boardForm")
	public String boardForm()
	{
		return "board/boardForm";
	}
	
	@RequestMapping("board/boardFormEnd.do")
	public String boardFormEnd(String boardTitle,
					String boardWriter, 
					String boardContent,
					MultipartFile[] upFile,
					HttpServletRequest request) throws BoardException
	{
		//board에 대한 값 title, comment.... 
		//파일 두개~~
		Map<String,String> board=new HashMap();
		board.put("title",boardTitle);
		board.put("writer",boardWriter);
		board.put("content",boardContent);
		
		ArrayList<Attachment> files=new ArrayList();
				
		
		String savDir=request.getSession()
							.getServletContext()
							.getRealPath("/resources/upload/board");
		for(MultipartFile f : upFile)
		{
			if(!f.isEmpty()) {
				//파일명을 생성(rename)
				String orifileName=f.getOriginalFilename();
				String ext=orifileName.substring(orifileName.lastIndexOf("."));
				//rename 규칙설정
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
				int rdv=(int)(Math.random()*1000);
				String reName=sdf.format(System.currentTimeMillis())+"_"+rdv+ext;
				//파일을 저장해보자
				try {
					f.transferTo(new File(savDir+"/"+reName));
				}catch(IllegalStateException | IOException e)
				{
					e.printStackTrace();
				}
				Attachment att=new Attachment();
				att.setReNamedFileName(reName);
				att.setOriginalFileName(orifileName);
				files.add(att);
			}
		}
		int result=service.insertBoard(board,files);
		
		
		
		return "redirect:/board/boardList";
	}
	
	@RequestMapping("/board/boardView.do")
	public ModelAndView boardView(int boardNo) {
		ModelAndView mv = new ModelAndView();
		Map<String, String> map = service.selectBoard(boardNo);
		List<Map<String, String>> attach = service.selectAttach(boardNo);
		
		mv.addObject("board", map);
		mv.addObject("attach", attach);
		mv.setViewName("board/boardView");
		
		return mv;
	}
	
	@RequestMapping("/board/fileDownLoad.do")
	public void fileDownLoad(String oName, String rName, HttpServletRequest request, HttpServletResponse response) {
		BufferedInputStream bis = null;
		ServletOutputStream sos = null;
		
		String dir = request.getSession().getServletContext().getRealPath("/resources/upload/board");
		File savedFile = new File(dir + "/" + rName);
		
		try {
			FileInputStream fis = new FileInputStream(savedFile);
			bis = new BufferedInputStream(fis);
			sos = response.getOutputStream();
			
			String resFileName = "";
			boolean isMSIE = request.getHeader("user-agent").indexOf("MSIE") != -1
					|| request.getHeader("user-agent").indexOf("Trident") != -1;
			
			if (isMSIE) {
				resFileName = URLEncoder.encode(oName, "UTF-8");
				resFileName = resFileName.replaceAll("\\+","%20");
			} else {
				resFileName = new String(oName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			response.setContentType("application/octet-stream;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + resFileName + "\"");
			// 파일길이 설정
			response.setContentLength((int) savedFile.length());
			
			int read = 0;
			
			while ((read=bis.read()) != -1) {
				sos.write(read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
