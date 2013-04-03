package cz.mzk.editor.client.gin;

import org.jboss.errai.bus.client.ErraiBus;
import org.jboss.errai.bus.client.framework.MessageBus;

import javax.inject.Provider;

/**
 * @author: Martin Rumanek
 * @version: 20.3.13
 */
public class MessageBusProvider implements Provider<MessageBus> {
    @Override
    public MessageBus get() {
        return ErraiBus.get();
    }
}
