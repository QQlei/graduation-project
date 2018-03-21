package com.graduation.domain.result;

import lombok.Getter;
import lombok.Setter;

public class Response {
	/** 返回信息码*/
	@Setter
	@Getter
	private String rspCode="000000";
	/** 返回信息内容*/
	@Setter
	@Getter
	private String rspMsg="操作成功";

	public Response() {
	}
	
	public Response(ExceptionMsg msg){
		this.rspCode=msg.getCode();
		this.rspMsg=msg.getMsg();
	}
	
	public Response(String rspCode) {
		this.rspCode = rspCode;
		this.rspMsg = "";
	}

	public Response(String rspCode, String rspMsg) {
		this.rspCode = rspCode;
		this.rspMsg = rspMsg;
	}

	@Override
	public String toString() {
		return "Response{" +
				"rspCode='" + rspCode + '\'' +
				", rspMsg='" + rspMsg + '\'' +
				'}';
	}
}
