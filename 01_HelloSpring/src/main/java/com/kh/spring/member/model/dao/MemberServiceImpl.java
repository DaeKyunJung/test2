package com.kh.spring.member.model.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.service.MemberDao;
import com.kh.spring.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao dao;
	
	
	
	@Override
	public int memberUpdate(Member m) {
		// TODO Auto-generated method stub
		return dao.memberUpdate(m);
	}

	@Override
	public Member selectOne(String userId) {
		return dao.selectOne(userId);
	}

	@Override
	public Map<String,String> login(Map<String, String> map) {
		return dao.login(map);
	}

	@Override
	public int memberEnroll(Member m) {
		return dao.memberEnroll(m);
	}

	@Override
	public int checkId(String userId) {
		return dao.checkId(userId);
	}
}



