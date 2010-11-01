/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.event;

import com.google.gwt.event.shared.GwtEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class GreetingSentEvent.
 */
public class GreetingSentEvent extends GwtEvent<GreetingSentEventHandler> {
	
	/** The TYPE. */
	public static Type<GreetingSentEventHandler> TYPE = new Type<GreetingSentEventHandler>();

	/** The name. */
	private final String name;
	
	/** The message. */
	private final String message;

	/**
	 * Instantiates a new greeting sent event.
	 *
	 * @param name the name
	 * @param message the message
	 */
	public GreetingSentEvent(final String name, final String message) {
		this.name = name;
		this.message = message;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<GreetingSentEventHandler> getAssociatedType() {
		return TYPE;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(final GreetingSentEventHandler handler) {
		handler.onGreetingSent(this);
	}
}