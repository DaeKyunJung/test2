package com.kh.spring.member.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.dao.MemberService;
import com.kh.spring.member.model.vo.Member;

@SessionAttributes("userId")
@Controller
public class MemberController {
	
	private Logger logger=Logger.getLogger(MemberController.class);
	
	@Autowired
	MemberService service;
	@Autowired
	BCryptPasswordEncoder pwEncoder;
	
	@RequestMapping("/member/memberUpdate.do")
	public ModelAndView memberUpdate(Member m)
	{
		int result=service.memberUpdate(m);
		String msg="";
		String loc="";
		if(result>0)
		{
			msg="회원정보수정완료";
			loc="/";
		}
		else {
			msg="회원정보수정실패";
			loc="/member/memberView.do?userId="+m.getUserId();
		}
		ModelAndView mv=new ModelAndView();
		mv.addObject("msg",msg);
		mv.addObject("loc",loc);
		mv.setViewName("common/msg");
		return mv;		
		
	}
	
	
	@RequestMapping("/member/memberView.do")
	public ModelAndView memberView(String userId)
	{
		Member m=service.selectOne(userId);
		System.out.println(m);
		ModelAndView mv=new ModelAndView();
		mv.addObject("member",m);
		mv.setViewName("member/memberView");
		return mv;
		
	}
	
	
	@RequestMapping("/member/memberLogin.do")
	//public String login(String userId, String password, Model model)
	public ModelAndView login(String userId, String password, Model model)
	{
		ModelAndView mv=new ModelAndView();
		
		logger.debug("로그인 제대로 들어옴!");
		logger.debug("파라미터확인 userId : "+userId+" password : "+password);
		
		
		
		//1.vo객체를 이용하는 방법
		//2. map!을 이용하는 방법
		Map<String,String> map=new HashMap();
		map.put("userId",userId);
		map.put("password",password);
		
		Map<String,String> result=service.login(map);
		logger.debug("로그인결과 : "+result);
		System.out.println(result);
		String msg="";
		String loc="/";
		if(result!=null)
		{
			if(pwEncoder.matches(password, result.get("PASSWORD")))
			{
				msg="로그인성공";
				/*session.setAttribute("userId", result.get("USERID"));*/
				//model.addAttribute("userId",result.get("USERID"));
				mv.addObject("userId",result.get("USERID"));
			}
			else
			{
				msg="패스워드가 일치하지 않습니다.";
			}
		}
		else {
			msg="아이디가 존재하지 않습니다.";
		}
		/*model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);*/
		mv.addObject("msg",msg);
		mv.addObject("loc",loc);
		
		mv.setViewName("common/msg");
		
/*		if(result!=null) {
			try {
				throw new NullPointerException();
			}
			catch(Exception e)
			{
				logger.error("내가 만든에러"+e.getMessage());
				throw new NullPointerException();
			}
		}*/
		//return "common/msg";
		return mv;
	}
	
	@RequestMapping("/member/memberlogout.do")
	public String memberLogout(SessionStatus status)
	{
		//SessionAttributes등록 로그인이면 : sessionStatus객체의 
		//setComplete()매소드 이용로그아웃 처리
		//HttpSession이용 로그인이면 : HttpSession.invalide();
		if(!status.isComplete())
		{
			status.setComplete();
		}
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/member/memberEnroll.do")
	public String memberEnroll()
	{
		return "member/memberEnroll";
	}
	
	@RequestMapping("/member/memberEnrollEnd.do")
	public String memberEnrollEnd(Member m,Model model)
	{
		System.out.println(m);
		String rawPw=m.getPassword();
		System.out.println("암호화전 " +rawPw);
		//System.out.println("암호화후 "+pwEncoder.encode(rawPw));		
		m.setPassword(pwEncoder.encode(rawPw));
		int result=service.memberEnroll(m);
		String msg="";
		String loc="/";
		if(result>0)
		{
			msg="회원가입을 성공하였습니다.";
		}
		else {
			msg="회원가입 실패하였습니다.";
		}
		model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);
		return "common/msg"; 
		
	}
	
	@RequestMapping("/member/checkId.do")
	/*public void checkId(String userId, HttpServletResponse response) throws IOException {
		logger.debug("중복체크");
		boolean isId = service.checkId(userId)==0?false:true;
		
		response.getWriter().print(isId);
	}*/
	
	public ModelAndView checkId(String userId, ModelAndView mv) throws UnsupportedEncodingException {
		Map map = new HashMap();
		boolean isId = service.checkId(userId)==0?false:true;
		map.put("isId", isId);
		List<String> list = new ArrayList();
		list.add("Yoo");
		list.add("Lee");
		list.add("Kim");
		list.add("Joo");
		
		mv.addAllObjects(map);
		mv.addObject("char", URLEncoder.encode("문자열", "UTF-8"));
		mv.addObject("num", 1);
		mv.addObject("list", list);
		
		mv.setViewName("jsonView");
		
		return mv;
	}
}
