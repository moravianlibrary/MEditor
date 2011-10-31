
package cz.fi.muni.xkremser.editor.server;

import java.util.Map;

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

public interface Z3950Client {

    Map<DublinCore, ModsCollectionClient> search(Constants.SEARCH_FIELD field, String what);

}
