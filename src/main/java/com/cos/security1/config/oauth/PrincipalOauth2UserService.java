package com.cos.security1.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//	
	@Autowired
	private UserRepository userRepository;
	
	// 구글로부터 받은 userRequest 데이터에 대한 후처리 되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		// 구글 로그인 버튼 클릭 -> 구글 로그인 창->로그인 완료-> code를 리턴(OAuth-Client라이브러리)->Access Token 요청
		// userRqquest 정보-> 회원 프로필 받아야 한다-> loadUser 함수 호출-> 구글로부터 회원프로필을 받아준다.
		System.out.println("userRequest:" +oauth2User.getAttributes());
		
		String provider = userRequest.getClientRegistration().getClientId(); //google
		String providerId=oauth2User.getAttribute("sub");
		String username = provider+"_"+providerId;
		String password = "겟인데어";
		String email = oauth2User.getAttribute("email");
		String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		if (userEntity == null) {
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
		}
		
		return new PrincipalDetails(userEntity,oauth2User.getAttributes());
	}
}
//@Service
//public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	// 구글로부터 받은 userRequest 데이터에 대한 후처리 되는 함수
//	@Override
//	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//		OAuth2User oauth2User = super.loadUser(userRequest);
//		// 구글 로그인 버튼 클릭 -> 구글 로그인 창->로그인 완료-> code를 리턴(OAuth-Client라이브러리)->Access Token 요청
//		// userRqquest 정보-> 회원 프로필 받아야 한다-> loadUser 함수 호출-> 구글로부터 회원프로필을 받아준다.
//		System.out.println("getAttributes : " + oauth2User.getAttributes());
//
//		String provider = userRequest.getClientRegistration().getRegistrationId(); // Google
//		String providerId = oauth2User.getAttribute("sub"); // Google ProviderId Sub
//		String username = provider + "_" + providerId; // google_1231241512831
//		String password = "1234"; // 크게 의미 없음.
//		String email = oauth2User.getAttribute("email");
//		String role = "ROLE_USER";
//
//		User userEntity = userRepository.findByUsername(username);
//
//		if (userEntity == null) {
//			userEntity = User.builder().username(username).password(password).email(email).role(role).provider(provider)
//					.providerId(providerId).build();
//			userRepository.save(userEntity);
//		}
//
//		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
//	}

