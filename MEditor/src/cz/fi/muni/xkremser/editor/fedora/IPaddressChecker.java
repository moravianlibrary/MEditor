package cz.fi.muni.xkremser.editor.fedora;

/**
 * Implemenation of this class is able to decide whether the request for an
 * object can be processed
 */
public interface IPaddressChecker {

	public boolean privateVisitor();

	public boolean localHostVisitor();
}
