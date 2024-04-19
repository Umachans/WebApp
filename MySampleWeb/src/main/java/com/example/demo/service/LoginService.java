package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	
	//@RequiredArgsConstructor：private finalで宣言したインスタンス日してnewした物を注入するコンストラクタを実装
	private final UserInfoRepository repository; //newされたものが入る
	
	//repositoryクラスのfindByIdでログインIDをreturn(渡している。)
	//@Id属性のついたログインIDに対して引数のログインIDで検索をかけにいった結果を返す。
	public Optional<UserInfo> searchUserById(String loginId){
		
		//select * from userInfo テーブル名　where loginId=loginId
		return repository.findById(loginId);//戻り値がOptional<UserInfo>型
	}
	

}
