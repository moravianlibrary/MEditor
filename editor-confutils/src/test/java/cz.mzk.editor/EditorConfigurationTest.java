package cz.mzk.editor;

import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.config.EditorConfigurationImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class EditorConfigurationTest {
    private static final String IMAGE_SERVER_URL_KEY = "imageServer_url";
    private static final String IMAGE_SERVER_URL_VALUE = "http://imageserver.mzk.cz/";

    private static final String IMAGE_EXTENSION_KEY = "imageExtension";
    private static final String IMAGE_EXTENSION_VALUE = "jpg, tiff";

    private static final String DOCUMENT_TYPES_KEY = "documentTypes";
    private static final String DOCUMENT_TYPES_VALUE = "periodical, monograph";

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Before
    public void setUp() {
        environmentVariables.set(IMAGE_SERVER_URL_KEY, IMAGE_SERVER_URL_VALUE);
        environmentVariables.set(IMAGE_EXTENSION_KEY, "some-shadowed-value");
        environmentVariables.set(DOCUMENT_TYPES_KEY, DOCUMENT_TYPES_VALUE);
    }

    /**
     * The goal is to test if keys like:
     * `imageServer_url` are translated into `imageServer.url`
     *
     * Other goal is to test whether `compositeConfiguration works correctly.
     * CompositeConfiguration is loaded:
     * - firstly with `configuration.properties` file
     * - secondly with environmental variables (from docker-compose.yml)
     * If variable exists in both configurations it is loaded from `configuration.properties` file (shadowing env. variable)
     */
    @Test
    public void testConfigurationEnvironmentalVariables() {
        File propertiesFile = new File(EditorConfigurationImpl.CONFIGURATION);
        if (!propertiesFile.exists()) {
            BufferedWriter output = null;
            try {
                // firstly create new configuration.properties
                output = new BufferedWriter(new FileWriter(propertiesFile));
                output.write(IMAGE_EXTENSION_KEY + "=" + IMAGE_EXTENSION_VALUE);
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // create CompositeConfiguration
            EditorConfiguration editorConfiguration = new EditorConfigurationImpl();
            assertEquals("test env. variable imageServer_url => imageServer.url", editorConfiguration.getImageServerUrl(), IMAGE_SERVER_URL_VALUE);
            assertArrayEquals("test property file variable imageExtension (shadowing) env. variable", editorConfiguration.getImageExtensions(), IMAGE_EXTENSION_VALUE.split(", "));
            assertArrayEquals("test env. variable documentTypes", editorConfiguration.getDocumentTypes(), DOCUMENT_TYPES_VALUE.split(", "));
        } else {
            System.out.println("Skipping test - configuration.property file exists");
        }
    }
}