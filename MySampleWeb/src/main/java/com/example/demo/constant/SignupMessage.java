package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignupMessage {
	
	//成功の定数
	SUCCEED(MessageConst.SIGNUP_RESIST_SUCCEED,false),
	
	//失敗の定数
	EXISITED_LOGIN_ID(MessageConst.SIGNUP_EXISTED_LOGIN_ID,true);
	
	
	private String messageId;
	
	private boolean isErrror;

}
