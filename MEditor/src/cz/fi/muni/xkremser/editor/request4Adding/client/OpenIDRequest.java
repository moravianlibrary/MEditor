package cz.fi.muni.xkremser.editor.request4Adding.client;

import java.util.Date;

public class OpenIDRequest {
	private long id;
	private String name;
	private String openID;
	private Date timestamp;

	public OpenIDRequest(long id, String name, String openID, Date timestamp) {
		super();
		this.id = id;
		this.name = name;
		this.openID = openID;
		this.timestamp = timestamp;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
