package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

//JpaRepository가 CRUD 함수를 기본적으로 가지고 있음.
//@Repository 라는 어노테이션이 없어도 IoC가 된다.->JpaRepository가 상속했기 때문.

public interface UserRepository extends JpaRepository<User, Integer>{

	// findBy까지는 규칙 -> Username 문법. 
	// select * from user where username = ?
	public User findByUsername(String username); //JPA Querty methods 
}
