package cz.mzk.editor.client.gin;

import org.jboss.errai.bus.client.ErraiBus;
import org.jboss.errai.bus.client.api.ClientMessageBus;
import org.jboss.errai.bus.client.api.messaging.MessageBus;
import org.jboss.errai.bus.client.framework.ClientMessageBusImpl;

import javax.inject.Provider;

/**
 * @author: Martin Rumanek
 * @version: 20.3.13
 */
public class MessageBusProvider implements Provider<ClientMessageBus> {
    @Override
    public ClientMessageBus get() {
        return (ClientMessageBus) ErraiBus.get();
    }
}
