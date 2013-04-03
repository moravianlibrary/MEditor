package cz.mzk.editor.server;

import com.google.inject.Injector;
import cz.mzk.editor.server.guice.GuiceFactory;
import cz.mzk.editor.server.quartz.GuiceJobFactory;
import cz.mzk.editor.server.quartz.Quartz;
import org.jboss.errai.common.client.api.ResourceProvider;
import org.jboss.errai.common.server.api.ErraiConfig;
import org.jboss.errai.common.server.api.ErraiConfigExtension;
import org.jboss.errai.common.server.api.annotations.ExtensionComponent;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Collection;

/**
 * @author: Martin Rumanek
 * @version: 24.2.13
 */
@ExtensionComponent
public class GuiceErraiExtension implements ErraiConfigExtension {



        /* (non-Javadoc)
         * @see org.jboss.errai.bus.server.api.ErraiConfigExtension#configure(org.jboss.errai.bus.server.api.ErraiConfig)
         */
        @Override
        public void configure(ErraiConfig config) {
            Injector guice = GuiceFactory.getInjector();
            final Quartz quartz = guice.getInstance(Quartz.class);

            config.addBinding(Quartz.class, new ResourceProvider() {
                @Override
                public Object get() {
                    return quartz;
                }
            });
        }

}
