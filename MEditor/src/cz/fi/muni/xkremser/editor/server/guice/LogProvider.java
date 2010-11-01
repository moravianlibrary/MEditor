/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.guice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;

import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class LogProvider.
 */
@Singleton
public class LogProvider implements Provider<Log> {

	/* (non-Javadoc)
	 * @see com.google.inject.Provider#get()
	 */
	public Log get() {
		return new Log4JLogger("MeditorLogger");
	}

}