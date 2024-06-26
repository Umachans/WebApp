package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserListInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserListForm;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;
//UserListServiceクラスを実装したクラス

@Service
@RequiredArgsConstructor
public class UserListServiceImpl  implements UserListService{
	
	private final UserInfoRepository repository;
	
	private final Mapper mapper;
	
	
	
	
	
	@Override
	public List<UserListInfo> editUserList() {
		return toUserListInfos(repository.findAll());
	}
	/*ユーザー情報を条件検索した結果を画面の表示用に変換して返す*/
	public List<UserListInfo>editUserListByParam(UserListForm form){
		var userInfo=mapper.map(form,UserInfo.class);
		return toUserListInfos(findUserInfoByParam(userInfo));
	}
	
	/**
	 * ユーザー情報取得(条件付き)
	 * ユーザー情報を条件検索する
	 * @param form 入力情報
	 * @return 検索結果
	 */
	public List<UserInfo>findUserInfoByParam(UserInfo userInfo){
		var loginIdParam =AppUtil.addWildcard(userInfo.getLoginId());
		
		if(userInfo.getUserStatusKind()!=null && userInfo.getAuthorityKind()!=null) {
			return repository.findByLoginIdLikeAndUserStatusKindAndAuthorityKind(loginIdParam,
					userInfo.getUserStatusKind(),userInfo.getAuthorityKind());
		}else if(userInfo.getUserStatusKind()!=null) {
			return repository.findByLoginIdLikeAndUserStatusKind(loginIdParam,userInfo.getUserStatusKind());
		}else if(userInfo.getAuthorityKind()!=null) {
			return repository.findByLoginIdLikeAndAuthorityKind(loginIdParam,userInfo.getAuthorityKind());
		}else {
			return repository.findByLoginIdLike(loginIdParam);
		}
		
		
	}
	

	/**
	 * ユーザー情報EntityのListをユーザー一覧情報DTOのListに変換します。
	 * 
	 * @param userInfos ユーザー情報EntityのList
	 * @return ユーザ一覧情報DTOのList
	 */
	private List<UserListInfo> toUserListInfos(List<UserInfo> userInfos) {
		var userListInfos = new ArrayList<UserListInfo>();
		for (UserInfo userInfo : userInfos) {
			var userListInfo = mapper.map(userInfo, UserListInfo.class);
			userListInfo.setStatus(userInfo.getUserStatusKind().getDisplayValue());
			userListInfo.setAuthority(userInfo.getAuthorityKind().getDisplayValue());
			userListInfos.add(userListInfo);
		}

		return userListInfos;

	}

	

}
