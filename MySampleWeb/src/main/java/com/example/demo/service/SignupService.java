package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;

public interface SignupService {
	
	public Optional<UserInfo>resistUserInfo(SignupForm form);

}
