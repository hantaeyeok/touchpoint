package com.touchpoint.kh.user.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.vo.User;
import com.touchpoint.kh.user.model.vo.UserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService{

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	//user id checked
	@Override
	public Boolean userIdChecked(String userId) {
		return userRepository.findByUserId(userId).isEmpty();
	}
	
	@Override
	public Boolean userEmailChecked(String email) {
		return userRepository.findByEmail(email).isEmpty();
	}
	
	
	@Override
	public User signupGeneralUser(UserDto userDto) {
		    
	    //비밀번호 암호화 bc
	    String encodePassword = bCryptPasswordEncoder.encode(userDto.getPassword());
	    
	    //Dto -> Vo -> save
		User user = User.builder()
		                .userId(userDto.getUserId())
		                .password(encodePassword) 
		                .email(userDto.getEmail())
		                //전화번호 front에서 010-1234-1234 or 01012341234              
		                .phoneNo(userDto.getPhone().replaceAll("-", ""))                 
		                .name(userDto.getName())
		                .adAgreed(userDto.isAdAgreed() ? "Y" : "N")
		                .joinDate(java.time.LocalDate.now())
		                .userStatus("Y")
		                .socialUser("N")
		                .build();
		
		return userRepository.save(user);
	}




	



}
