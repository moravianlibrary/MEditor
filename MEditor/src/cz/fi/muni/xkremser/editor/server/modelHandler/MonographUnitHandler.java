package cz.fi.muni.xkremser.editor.server.modelHandler;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import cz.fi.muni.xkremser.editor.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;

public class MonographUnitHandler extends DigitalObjectHandler {

	@Inject
	public MonographUnitHandler(Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
		super(logger, fedoraAccess);
	}

	@Override
	public AbstractDigitalObjectDetail getDigitalObject(String uuid) {
		System.out.println("ahoj");
		return null;
	}

}
