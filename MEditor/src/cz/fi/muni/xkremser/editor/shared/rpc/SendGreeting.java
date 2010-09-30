package cz.fi.muni.xkremser.editor.shared.rpc;

import net.customware.gwt.dispatch.shared.Action;

public class SendGreeting implements Action<SendGreetingResult> {

	private static final long serialVersionUID = 5804421607858017477L;

	private String name;

	@SuppressWarnings("unused")
	private SendGreeting() {
	}

	public SendGreeting(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}