package cz.fi.muni.xkremser.editor.server.modelHandler;

import java.io.IOException;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

import cz.fi.muni.xkremser.editor.client.Constants.KrameriusModel;
import cz.fi.muni.xkremser.editor.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.fedora.KrameriusModelHelper;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;

public class DigitalObjectHandler implements CanGetObject {

	private final FedoraAccess fedoraAccess;
	private final Log logger;
	@Inject
	private Injector injector;

	@Inject
	public DigitalObjectHandler(final Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
		this.logger = logger;
		this.fedoraAccess = fedoraAccess;
	}

	@Override
	public AbstractDigitalObjectDetail getDigitalObject(String uuid) {
		KrameriusModel model = null;
		try {
			model = fedoraAccess.getKrameriusModel(uuid);
		} catch (IOException e) {
			logger.error("Could not get model of object " + uuid, e);
			return null;
		}
		CanGetObject handler = injector.getInstance(KrameriusModelHelper.TYPES.get(model));

		return handler.getDigitalObject(uuid);
	}

	public FedoraAccess getFedoraAccess() {
		return fedoraAccess;
	}

}
