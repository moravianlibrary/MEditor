package cz.mzk.editor.server.erraiHandler;

import cz.mzk.editor.server.handler.InsertNewDigitalObjectHandler;
import org.jboss.errai.bus.client.api.messaging.RequestDispatcher;
import org.jboss.errai.bus.server.annotations.Service;

import javax.inject.Inject;

/**
 * @author: Martin Rumanek
 * @version: 10.4.13
 */

@Service
public class InputQueueService {

    @Inject
    public InputQueueService(final RequestDispatcher dispatcher) {
        InsertNewDigitalObjectHandler.setErraiDispatcher(dispatcher);
    }
}
