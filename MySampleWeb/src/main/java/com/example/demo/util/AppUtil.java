package com.example.demo.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * アプリケーション共通クラス
 */
public class AppUtil {
	
	/**
	 * メッセージIDからメッセージソースを取得する
	 * @param messagesource　メッセージソース
	 * @param key　メッセージキー
	 * @param params　置換文字
	 * @return　メッセージ
	 */
	
	public static String getMessage(MessageSource messagesource,String key,Object...params) {
		return messagesource.getMessage(key,params,Locale.JAPAN);
	}
	
	/**
	 * DBのLike検索用に、パラメーターにワイルドカードを付与します
	 * 
	 * @param param パラメーター
	 * @return 前後にワイルドカードがついたパラメーター
	 */
	public static String addWildcard(String param) {
		return "%" + param + "%";
	}

}
