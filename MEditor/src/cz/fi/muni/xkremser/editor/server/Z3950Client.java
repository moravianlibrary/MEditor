package cz.fi.muni.xkremser.editor.server;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

public interface Z3950Client {

	List<DublinCore> search(Constants.SEARCH_FIELD field, String what);

}
