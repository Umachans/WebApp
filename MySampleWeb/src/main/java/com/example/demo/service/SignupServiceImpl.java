package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.UserInfoRepository;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;


/*ユーザー登録画面*/
@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService{
	
	/*ユーザー情報テーブルDAO*/
	private final UserInfoRepository repository; //newされたものが入る
	
	/*Dozer Mapper*/
	private final Mapper mapper;
	
	/*PasswordEncoder*/
	//PasswordEncoderの中にBCryptPasswordEncoderが入る
	private final PasswordEncoder passwordEncoder ;
	
	/**
	 * ユーザー情報テーブル　新規登録
	 * @param form　入力情報
	 * @return　登録情報(ユーザー情報Entity)、既に同じユーザーIDで登録がある場合はEmpty
	 */
	@Override
	public Optional< UserInfo >resistUserInfo(SignupForm form){
		var userInfoExistedOpt=repository.findById(form.getLoginId());
		if(userInfoExistedOpt.isPresent()) {//あればOptional.empty();を返す。
			return Optional.empty();
		}

		var userInfo= mapper.map(form, UserInfo.class);//なければこの処理にうつりデータ登録を行う。
		var  encodedPassword=passwordEncoder.encode(form.getPassword());//ハッシュ化したPWをゲット
		userInfo.setPassword(encodedPassword);//userInfoにハッシュ化したPWを入れる
		userInfo.setUserStatusKind(UserStatusKind.ENABLED);
		userInfo.setAuthorityKind(AuthorityKind.ITEM_WATCHER);
		userInfo.setCreateTime(LocalDateTime.now()); //登録が行われた時間が入る
		userInfo.setUpdateTime(LocalDateTime.now());
		userInfo.setUpdateUser(form.getLoginId());
		
		return Optional.of(repository.save(userInfo)); //repositoryクラスのメソッドsaveにuserInfoの変数を渡す
	}
	

}
