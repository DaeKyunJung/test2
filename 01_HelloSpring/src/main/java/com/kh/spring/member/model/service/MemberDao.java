package com.kh.spring.member.model.service;

import java.util.Map;

import com.kh.spring.member.model.vo.Member;

public interface MemberDao {

	Map<String,String> login(Map<String,String> map);
	int memberEnroll(Member m);
	Member selectOne(String userId);
	int memberUpdate(Member m);
	int checkId(String userId);
}
