package com.example.demo.dgut.util;

import lombok.Data;


@Data
public class JsonDataResult {

	private int code;
	private Object data;
	private String msg;

	private JsonDataResult(int code, Object data, String msg) {
		super();
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

	/**
	 * 返回业务成功的结果json
	 * @param object 成功后的对象
	 * @return 状态码code 1， 以及data包含返回对象
	 */
	public static JsonDataResult buildSuccess(Object object) {
		return new JsonDataResult(1, object, "操作成功");
	}
	/**
	 * 返回业务错误的结果json
	 * @param err 错误信息
	 * @return 状态码code -1， 以及错误信息
	 */
	public static JsonDataResult buildError(String err) {
		return new JsonDataResult(-1, null, err);
	}

	/**
	 * 自定义返回业务的结果json
	 * @param code 状态码
	 * @param object 返回的对象
	 * @param msg 返回的消息
	 * @return
	 */
	public static JsonDataResult buildCustom(int code, Object object, String msg) {
		return new JsonDataResult(code, object, msg);
	}

}
