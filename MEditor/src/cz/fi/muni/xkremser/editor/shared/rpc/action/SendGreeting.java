package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

import cz.fi.muni.xkremser.editor.shared.rpc.result.SendGreetingResult;

public class SendGreeting extends UnsecuredActionImpl<SendGreetingResult> {

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