/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.rpc.result;

import com.gwtplatform.dispatch.shared.Result;

// TODO: Auto-generated Javadoc
/**
 * The Class SendGreetingResult.
 */
public class SendGreetingResult implements Result {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7917449246674223581L;

	/** The name. */
	private String name;
	
	/** The message. */
	private String message;

	/**
	 * Instantiates a new send greeting result.
	 *
	 * @param name the name
	 * @param message the message
	 */
	public SendGreetingResult(final String name, final String message) {
		this.name = name;
		this.message = message;
	}

	/**
	 * Instantiates a new send greeting result.
	 */
	@SuppressWarnings("unused")
	private SendGreetingResult() {
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
}