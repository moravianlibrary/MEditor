package cz.fi.muni.xkremser.editor.shared.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InputQueueItem implements IsSerializable {

	private String path;
	private String name;
	private String issn;

	// @SuppressWarnings("unused")
	public InputQueueItem() {

	}

	public InputQueueItem(String path, String issn, String name) {
		super();
		this.path = path;
		this.name = name;
		this.issn = issn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	@Override
	public String toString() {
		return "(" + this.path + ", " + this.name + ", " + this.issn + ")";
	}

}
