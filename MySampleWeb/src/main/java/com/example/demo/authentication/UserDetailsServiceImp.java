package com.example.demo.authentication;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;


/**
 * ユーザー情報生成
 */
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
	
	/*ユーザー情報テーブルRepository*/
	private final UserInfoRepository repository ;
	
	
    /*アカウントロック*/
	@Value("${security.locking-border-count}")
	private  int lockingBorderCount;
	
	/*アカウントロック持続時間*/
	@Value("${security.locking-time}")
	private int lockingTime;
	
	
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var userInfo = repository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));

		var accountLockedTime = userInfo.getAccountLockedTime();
		var isAccountLocked = accountLockedTime != null
				&& accountLockedTime.plusHours(lockingTime).isAfter(LocalDateTime.now());


		return User.withUsername(userInfo.getLoginId())
				.password(userInfo.getPassword())
				.authorities(userInfo.getAuthorityKind().getCode())
				.disabled(userInfo.getUserStatusKind().isDisabled())
				.accountLocked(isAccountLocked)
				.build();
	}
	
	/**
	 * 認証失敗時にログイン失敗回数を加算、ロック日時を更新する
	 * @param event イベント情報
	 */
	@EventListener
	public void handle(AuthenticationFailureBadCredentialsEvent event) {
		var loginId=event.getAuthentication().getName();
		repository.findById(loginId).ifPresent(userInfo ->{
			repository.save(userInfo.incrementLoginFailureCount());
			
			var isReachFailureCount=userInfo.getLoginFailureCount()==lockingBorderCount;
			if(isReachFailureCount) {
				repository.save(userInfo.updateAccountLocked());
			}
		});
	}
	
	@EventListener
	public void handle(AuthenticationSuccessEvent event) {
		var loginId=event.getAuthentication().getName();
		repository.findById(loginId).ifPresent(userInfo ->{
			repository.save(userInfo.resetLoginFailureInfo());
		});
	}
	
	

}
