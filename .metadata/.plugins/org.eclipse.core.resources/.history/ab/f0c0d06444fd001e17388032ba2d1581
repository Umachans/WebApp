package com.example.demo.controller;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.SignupMessage;
import com.example.demo.constant.UrlConst;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.service.SignupService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー登録画面
 */
@Controller
@RequiredArgsConstructor
//@RequestMapping("/login")
public class SignupController {
	
	/*登録画面 Service*/
	private final SignupService service;
	
	/*メッセージソース*/
	private final MessageSource messagesource;
	
	
	
	@GetMapping(UrlConst.SIGNUP)
	public String view(Model model,SignupForm form) {
		
		
		return "signup";
	}
	
	
	@PostMapping("/signup") // /loginのURLで受け取る
	public void  signup(Model model,@Validated SignupForm form,BindingResult bdResult) {//Modelクラスを使えば画面にControllerからの情報を渡せる
		                                //@Validated:formの@チェックが入るBindingResult:@Validatedがついたフォームの入力チェック結果が保管される
		if(bdResult.hasErrors()) { //hasErrors：エラーが会った時の処理を実装できる
			editGuideMessage(model,MessageConst.FORM_ERROR,true);
			return;
			
		}
		var userInfoOpt=service.resistUserInfo(form); //ContorollerでServiceから戻ってきた変数をチェック
		var signupMessage =judgeMessageKey(userInfoOpt); 
			editGuideMessage(model,signupMessage.getMessageId(),signupMessage.isErrror());
			
	}
	/**
	 * 画面に表示するガイドメッセージを設定する
	 * @param model　モデル
	 * @param messageId　メッセージID
	 * @param isError エラー有無
	 */
	private void editGuideMessage(Model model,String messageId,boolean isError) {
		var message=AppUtil.getMessage(messagesource,messageId);
		model.addAttribute("message", message);
		model.addAttribute("isError",isError);
	}
	
	
	
	
	//メッセージ内容を変えるメソッド
	private SignupMessage judgeMessageKey(Optional<UserInfo>userInfoOpt) {
		if(userInfoOpt.isEmpty()) {
			return SignupMessage.EXISITED_LOGIN_ID;
		}else {
			return SignupMessage.SUCCEED;
		}
	}
	
	
}
