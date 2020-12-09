package com.elsa.ttm.domain;

import java.util.ArrayList;
import java.util.List;

public class AssignDO {

	private String ip;

	private int num;

	private List<String> params = new ArrayList<String>();

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

}
