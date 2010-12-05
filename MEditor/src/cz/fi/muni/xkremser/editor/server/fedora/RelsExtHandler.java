/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.fedora;

import org.w3c.dom.Element;

import cz.fi.muni.xkremser.editor.client.FedoraRelationship;

// TODO: Auto-generated Javadoc
/**
 * Interface allows to user to be informed of events during processRelsExt
 * method call.
 * 
 * @author pavels
 * @see FedoraAccess#processRelsExt(org.w3c.dom.Document, RelsExtHandler)
 * @see FedoraAccess#processRelsExt(String, RelsExtHandler)
 */
public interface RelsExtHandler {

	/**
	 * Accept or deny this relation. If method returns false, algortighm skip this
	 * element and continues with next
	 * 
	 * @param relation
	 *          Relation type
	 * @return true, if successful
	 */
	public boolean accept(FedoraRelationship relation);

	/**
	 * Handle processing element.
	 * 
	 * @param elm
	 *          Processing element
	 * @param relation
	 *          Type of relation
	 * @param level
	 *          the level
	 */
	public void handle(Element elm, FedoraRelationship relation, int level);

	/**
	 * Break process.
	 * 
	 * @return true, if successful
	 */
	public boolean breakProcess();
}
