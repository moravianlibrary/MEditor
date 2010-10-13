package cz.fi.muni.xkremser.editor.fedora;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.fedora.api.FedoraAPIA;
import org.fedora.api.FedoraAPIM;
import org.fedora.api.ObjectFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * This is secured variant of class FedoraAccessImpl {@link FedoraAccessImpl}. <br>
 * Only three methos are secured:
 * <ul>
 * <li>FedoraAccess#getImageFULL(String)</li>
 * <li>FedoraAccess#isImageFULLAvailable(String)</li>
 * <li>FedoraAccess#getImageFULLMimeType(String)</li>
 * </ul>
 * 
 * @see FedoraAccess#getImageFULL(String)
 * @see FedoraAccess#isImageFULLAvailable(String)
 * @see FedoraAccess#getImageFULLMimeType(String)
 * @author pavels
 */
public class SecuredFedoraAccessImpl implements FedoraAccess {

	private final FedoraAccess rawAccess;
	private IPaddressChecker acceptor;

	@Inject
	public SecuredFedoraAccessImpl(@Named("rawFedoraAccess") FedoraAccess rawAccess) {
		super();
		this.rawAccess = rawAccess;
	}

	@Override
	public Document getBiblioMods(String uuid) throws IOException {
		return rawAccess.getBiblioMods(uuid);
	}

	@Override
	public Document getDC(String uuid) throws IOException {
		return rawAccess.getDC(uuid);
	}

	@Override
	public InputStream getImageFULL(String uuid) throws IOException {
		if (!this.acceptor.privateVisitor()) {
			Document relsExt = this.rawAccess.getRelsExt(uuid);
			checkPolicyElement(relsExt);
		}
		return rawAccess.getImageFULL(uuid);
	}

	private void checkPolicyElement(Document relsExt) throws IOException {
		try {
			XPathFactory xpfactory = XPathFactory.newInstance();
			XPath xpath = xpfactory.newXPath();
			xpath.setNamespaceContext(new FedoraNamespaceContext());
			XPathExpression expr = xpath.compile("//kramerius:policy/text()");
			Object policy = expr.evaluate(relsExt, XPathConstants.STRING);
			if ((policy != null) && (policy.toString().trim().equals("policy:private"))) {
				throw new SecurityException("access denided");
			}
		} catch (XPathExpressionException e) {
			throw new IOException(e);
		}
	}

	@Override
	public String getImageFULLMimeType(String uuid) throws IOException, XPathExpressionException {
		if (!this.acceptor.privateVisitor()) {
			Document relsExt = this.rawAccess.getRelsExt(uuid);
			checkPolicyElement(relsExt);
		}
		return rawAccess.getImageFULLMimeType(uuid);
	}

	@Override
	public Document getImageFULLProfile(String uuid) throws IOException {
		return rawAccess.getImageFULLProfile(uuid);
	}

	@Override
	public KrameriusModels getKrameriusModel(Document relsExt) {
		return rawAccess.getKrameriusModel(relsExt);
	}

	@Override
	public KrameriusModels getKrameriusModel(String uuid) throws IOException {
		return rawAccess.getKrameriusModel(uuid);
	}

	@Override
	public List<Element> getPages(String uuid, boolean deep) throws IOException {
		return rawAccess.getPages(uuid, deep);
	}

	@Override
	public List<Element> getPages(String uuid, Element rootElementOfRelsExt) throws IOException {
		return rawAccess.getPages(uuid, rootElementOfRelsExt);
	}

	@Override
	public Document getRelsExt(String uuid) throws IOException {
		return rawAccess.getRelsExt(uuid);
	}

	@Override
	public InputStream getThumbnail(String uuid) throws IOException {
		return rawAccess.getThumbnail(uuid);
	}

	@Override
	public String getThumbnailMimeType(String uuid) throws IOException, XPathExpressionException {
		return rawAccess.getThumbnailMimeType(uuid);
	}

	@Override
	public Document getThumbnailProfile(String uuid) throws IOException {
		return rawAccess.getThumbnailProfile(uuid);
	}

	@Override
	public boolean isImageFULLAvailable(String uuid) throws IOException {
		// not checked method
		return rawAccess.isImageFULLAvailable(uuid);
	}

	@Override
	public boolean isContentAccessible(String uuid) throws IOException {
		if (!this.acceptor.privateVisitor()) {
			Document relsExt = this.rawAccess.getRelsExt(uuid);
			try {
				checkPolicyElement(relsExt);
			} catch (SecurityException e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void processRelsExt(Document relsExtDocument, RelsExtHandler handler) throws IOException {
		rawAccess.processRelsExt(relsExtDocument, handler);
	}

	@Override
	public void processRelsExt(String uuid, RelsExtHandler handler) throws IOException {
		rawAccess.processRelsExt(uuid, handler);
	}

	public IPaddressChecker getAcceptor() {
		return acceptor;
	}

	@Inject
	public void setAcceptor(IPaddressChecker acceptor) {
		this.acceptor = acceptor;
	}

	@Override
	public FedoraAPIA getAPIA() {
		return rawAccess.getAPIA();
	}

	@Override
	public FedoraAPIM getAPIM() {
		return rawAccess.getAPIM();
	}

	@Override
	public ObjectFactory getObjectFactory() {
		return rawAccess.getObjectFactory();
	}

	@Override
	public void processSubtree(String pid, TreeNodeProcessor processor) {
		rawAccess.processSubtree(pid, processor);
	}

	@Override
	public Set<String> getPids(String pid) {
		return rawAccess.getPids(pid);
	}

	@Override
	public InputStream getDataStream(String pid, String datastreamName) throws IOException {
		if (pid.startsWith("uuid:")) {
			String uuid = pid.substring("uuid:".length());
			if (!this.acceptor.privateVisitor()) {
				Document relsExt = this.rawAccess.getRelsExt(uuid);
				checkPolicyElement(relsExt);
			}
		}
		return rawAccess.getDataStream(pid, datastreamName);
	}

	@Override
	public String getMimeTypeForStream(String pid, String datastreamName) throws IOException {
		return rawAccess.getMimeTypeForStream(pid, datastreamName);
	}

	@Override
	public boolean isDigitalObjectPresent(String uuid) {
		return rawAccess.isDigitalObjectPresent(uuid);
	}

}
