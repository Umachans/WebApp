package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import com.example.demo.entity.UserInfo;

//DBから情報をとってくるクラス
/**
 * JpaRepositoryを継承
 * ジェネリクスの中にはEntityクラスを書く<UserInfo,String>
 * Stringは一つ目のデータの型(今回はString型)
 * 
 */

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,String>{
	
	/*
	 * ログインID部分一致検索を行います
	 * @param loginId ログインID
	 * @return 検索でヒットしたユーザーの情報リスト
	 * 
	 * */
	
	List<UserInfo>findByLoginIdLike(String loginId);
	
	/**
	 * ログインID、アカウント状態の項目を使って検索を行います。
	 * @param loginId ログインID
	 * @param userStatusKind アカウント状態
	 * @return 検索でヒットしユーザー情報リスト
	 */
	List<UserInfo>findByLoginIdLikeAndUserStatusKind(String loginId,UserStatusKind userStatusKind);
	
	/**
	 * ログインID、検索項目を絞って検索を行う
	 * @param loginId ログインID
	 * @param authorityKind 権限
	 * @return 検索でユーザーヒットした情報のリスト
	 */
	List<UserInfo>findByLoginIdLikeAndAuthorityKind(String loginId,AuthorityKind authorityKind);
	
	/**
	 * ログインID、アカウント状態、権限の項目を使って検索をする
	 * 
	 * @param loginId ログインID
	 * @param userStatusKind アカウント状態
	 * @param authorityKind 権限
	 * @return 検索でヒットしたユーザーの権限リスト
	 */
	List<UserInfo>findByLoginIdLikeAndUserStatusKindAndAuthorityKind(String loginId,UserStatusKind userStatusKind,
			AuthorityKind authorityKind);
	
	
	

}
