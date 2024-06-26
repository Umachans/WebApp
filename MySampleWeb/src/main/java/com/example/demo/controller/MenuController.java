
package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UrlConst;

@Controller
public class MenuController {
	/**
	 * 初期表示
	 * @return user ユーザー情報
	 * @param model モデル
	 * @return 表示画面
	 */
	@GetMapping(UrlConst.MENU)
	public String view(@AuthenticationPrincipal User user,Model model) {//引数にユーザーIDと画面の情報を渡す
		
		var hasUserManageAuth = user.getAuthorities().stream()          //権限情報取得
				.allMatch(authority -> authority.getAuthority()         //1件登録されている権限情報がマッチしているか
				   .equals(AuthorityKind.ITEM_AND_USER_MANAGER.getCode())); //getAuthorityの中身が3ならtrue
		model.addAttribute("hasUserManageAuth", hasUserManageAuth);
		
		
		return "menu";
	}

}
