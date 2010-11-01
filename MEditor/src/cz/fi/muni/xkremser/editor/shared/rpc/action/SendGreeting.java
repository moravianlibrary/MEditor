/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

import cz.fi.muni.xkremser.editor.shared.rpc.result.SendGreetingResult;

// TODO: Auto-generated Javadoc
/**
 * The Class SendGreeting.
 */
public class SendGreeting extends UnsecuredActionImpl<SendGreetingResult> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5804421607858017477L;

	/** The name. */
	private String name;

	/**
	 * Instantiates a new send greeting.
	 */
	@SuppressWarnings("unused")
	private SendGreeting() {
	}

	/**
	 * Instantiates a new send greeting.
	 *
	 * @param name the name
	 */
	public SendGreeting(final String name) {
		this.name = name;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}