package com.kh.spring.member.model.dao;

import java.util.Map;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {

	Map<String,String> login(Map<String,String> map);
	int memberEnroll(Member m);
	Member selectOne(String userId);
	int memberUpdate(Member m);
	int checkId(String userId);
}
