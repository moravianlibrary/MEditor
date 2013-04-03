package cz.mzk.editor.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author: Martin Rumanek
 * @version: 6.3.13
 */
public class GuiceFactory {
    private static Injector injector;

    public static Injector getInjector() {
        if (injector == null) {
            injector = Guice.createInjector(new ServerModule(), new DispatchServletModule());
        }
        return injector;
    }
}
