package cz.fi.muni.xkremser.editor.common;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RequestItem implements IsSerializable {
	private long id;
	private String name;
	private String openID;
	private String date;

	public RequestItem() {
	}

	public RequestItem(long id, String name, String openID, String date) {
		super();
		this.id = id;
		this.name = name;
		this.openID = openID;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String getTimestamp() {
		return date;
	}

	public void setTimestamp(String date) {
		this.date = date;
	}

}
