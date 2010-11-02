/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.fedora;

// TODO: Auto-generated Javadoc
/**
 * Implemenation of this class is able to decide whether the request for an
 * object can be processed.
 */
public interface IPaddressChecker {

	/**
	 * Private visitor.
	 *
	 * @return true, if successful
	 */
	public boolean privateVisitor();

	/**
	 * Local host visitor.
	 *
	 * @return true, if successful
	 */
	public boolean localHostVisitor();
}
