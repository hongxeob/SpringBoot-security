package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller
public class IndexController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({ "", "/" })
	public String index() {
		// 머스태치 기본 폴더 -> src/main/resource/
		// 뷰 리졸버 설정 : templates(prefix) .mustache(suffix) ->생략가능!
		return "index";
	}

	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}

	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}

	// 스프링시큐리티가 해당 주소를 낚아챔 -SecurityConfig 파일 생성 후 작동 안함.
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword(); //생 비밀번호(rawPass--)는 유저가 적은 걸 받은 패스워드이다.
		String encPaswword = bCryptPasswordEncoder.encode(rawPassword); //암호화 패스워드는(encPass--) rawPass--를 암호화 한것이다 
		user.setPassword(encPaswword); // DB로 날라오는것은 암호화된 encPass--이다.
		userRepository.save(user); // 그런 후 그걸 유저 레파지토리에 user 정보에 저장해준다.
		return "redirect:/loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}

}