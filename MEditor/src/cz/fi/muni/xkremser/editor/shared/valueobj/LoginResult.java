package cz.fi.muni.xkremser.editor.shared.valueobj;

import java.io.Serializable;

public class LoginResult implements Serializable {
	private boolean success;
	private String url;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
