package cz.fi.muni.xkremser.editor.shared.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InputQueueItemDetail implements IsSerializable {
	private String name;
	private String id;

	@SuppressWarnings("unused")
	private InputQueueItemDetail() {

	}

	public InputQueueItemDetail(String id, String name) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
