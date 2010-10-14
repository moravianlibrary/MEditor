package cz.fi.muni.xkremser.editor.server.modelHandler;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import cz.fi.muni.xkremser.editor.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.shared.rpc.action.AbstractDigitalObjectDetail;

public class PageHandler extends DigitalObjectHandler {

	@Inject
	public PageHandler(Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
		super(logger, fedoraAccess);
	}

	@Override
	public AbstractDigitalObjectDetail getDigitalObject(String uuid) {
		System.out.println("ahoj");
		return null;
	}

}
