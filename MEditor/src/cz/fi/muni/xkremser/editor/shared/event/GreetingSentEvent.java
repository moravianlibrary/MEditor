package cz.fi.muni.xkremser.editor.shared.event;

import com.google.gwt.event.shared.GwtEvent;

public class GreetingSentEvent extends GwtEvent<GreetingSentEventHandler> {
	public static Type<GreetingSentEventHandler> TYPE = new Type<GreetingSentEventHandler>();

	private final String name;
	private final String message;

	public GreetingSentEvent(final String name, final String message) {
		this.name = name;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public Type<GreetingSentEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final GreetingSentEventHandler handler) {
		handler.onGreetingSent(this);
	}
}