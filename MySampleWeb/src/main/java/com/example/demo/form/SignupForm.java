package com.example.demo.form;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/*ユーザー登録画面
 * */


@Data
public class SignupForm {
	/*ログインID:最小8桁、最大20桁で入力*/
	@Length(min=8,max=20)
	private String loginId;
	/*パスワード*/
	@Length(min=8,max=20)
	private String password;

}
