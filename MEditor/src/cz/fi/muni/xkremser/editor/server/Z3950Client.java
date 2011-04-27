package cz.fi.muni.xkremser.editor.server;

import cz.fi.muni.xkremser.editor.client.util.Constants;

public interface Z3950Client {

	Object search(Constants.SEARCH_FIELD field, String what);

}
