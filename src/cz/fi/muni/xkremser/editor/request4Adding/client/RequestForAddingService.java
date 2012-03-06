
package cz.fi.muni.xkremser.editor.request4Adding.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ask")
public interface RequestForAddingService
        extends RemoteService {

    String ask();

    String getName();

}
