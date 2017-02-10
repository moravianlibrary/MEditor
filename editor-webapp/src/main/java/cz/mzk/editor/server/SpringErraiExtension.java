package cz.mzk.editor.server;

import cz.mzk.editor.server.quartz.Quartz;
import org.jboss.errai.common.server.api.ErraiConfig;
import org.jboss.errai.common.server.api.ErraiConfigExtension;
import org.jboss.errai.common.server.api.annotations.ExtensionComponent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.beans.Introspector;

/**
 * Created by rumanekm on 10.2.17.
 */
@ExtensionComponent
@Component
public class SpringErraiExtension implements ErraiConfigExtension, ApplicationContextAware {

    private static ApplicationContext ctx;

    /* (non-Javadoc)
     * @see org.jboss.errai.bus.server.api.ErraiConfigExtension#configure(org.jboss.errai.bus.server.api.ErraiConfig)
     */
    @Override
    public void configure(ErraiConfig config) {

        final Quartz quartz = this.ctx.getBean(Introspector.decapitalize("quartz"), Quartz.class);
        config.addBinding(Quartz.class, () -> quartz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
