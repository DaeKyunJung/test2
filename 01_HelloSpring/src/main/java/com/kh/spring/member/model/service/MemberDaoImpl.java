package com.kh.spring.member.model.service;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository
public class MemberDaoImpl implements MemberDao {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	
	
	@Override
	public int memberUpdate(Member m) {
		// TODO Auto-generated method stub
		return sqlSession.update("member.memberUpdate",m);
	}

	@Override
	public Member selectOne(String userId) {
		return sqlSession.selectOne("member.selectOne",userId);
	}

	@Override
	public Map<String,String> login(Map<String, String> map) {
		return sqlSession.selectOne("member.login",map);
	}

	@Override
	public int memberEnroll(Member m) {
		return sqlSession.insert("member.insertMember",m);
	}

	@Override
	public int checkId(String userId) {
		return sqlSession.selectOne("member.checkId", userId);
	}
}
