package com.stakhiyevich.openadboard.util.locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

import static com.stakhiyevich.openadboard.util.MessageKey.LOCALIZATION_PREFIX;

/**
 * The resource bundle manager specifies resources for localized messages.
 */
public class ResourceBundleManager {

    private static final Logger logger = LogManager.getLogger();

    private static final String DEFAULT_LOCALIZATION = "en";

    private static ResourceBundleManager instance;

    private ResourceBundleManager() {
    }

    public static ResourceBundleManager getInstance() {
        if (instance == null) {
            instance = new ResourceBundleManager();
        }
        return instance;
    }

    /**
     * Gets a resource bundle with a specified language, or the default language if the specified language was not found
     *
     * @param selectedLanguage a language
     * @return a resource bundle object
     */
    public ResourceBundle getResourceBundle(String selectedLanguage) {
        ResourceBundle resourceBundle;
        if (selectedLanguage != null) {
            try {
                resourceBundle = ResourceBundle.getBundle(LOCALIZATION_PREFIX + selectedLanguage);
            } catch (IllegalArgumentException e) {
                logger.warn("the language {} is not found", selectedLanguage, e);
                resourceBundle = ResourceBundle.getBundle(LOCALIZATION_PREFIX + DEFAULT_LOCALIZATION);
            }
        } else {
            resourceBundle = ResourceBundle.getBundle(LOCALIZATION_PREFIX + DEFAULT_LOCALIZATION);
        }
        return resourceBundle;
    }
}
