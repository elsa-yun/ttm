package com.elsa.ttm.domain;

import java.util.List;

/**
 * @author longhaisheng
 *
 */
public class ResultDO {

	private boolean success;

	private String errorCode;

	private List<String> successIps;

	private List<String> failIps;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getSuccessIps() {
		return successIps;
	}

	public void setSuccessIps(List<String> successIps) {
		this.successIps = successIps;
	}

	public List<String> getFailIps() {
		return failIps;
	}

	public void setFailIps(List<String> failIps) {
		this.failIps = failIps;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
