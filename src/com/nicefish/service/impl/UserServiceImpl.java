package com.nicefish.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicefish.dao.UserMapper;
import com.nicefish.model.User;
import com.nicefish.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	@Override
	public User findById(String id) {
		return userMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<User> findAll() {
		return userMapper.findAll();
	}


	@Override
	public int delete(String id) {
		return userMapper.deleteByPrimaryKey(id);
	}


	@Override
	public User findByCode(String code) {
		return userMapper.findByCode(code);
	}


	@Override
	public int update(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public boolean createUser(User user) {
		return false;
	}


	@Override
	public int insert(User user) {
		return userMapper.insertSelective(user);
	}
}
