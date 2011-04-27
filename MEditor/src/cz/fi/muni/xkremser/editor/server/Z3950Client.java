package cz.fi.muni.xkremser.editor.server;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.util.Constants;

public interface Z3950Client {

	List<String> search(Constants.SEARCH_FIELD field, String what);

}
