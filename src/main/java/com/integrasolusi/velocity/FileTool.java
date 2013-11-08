package com.integrasolusi.velocity;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 10:34 AM
 */
public class FileTool {
    private static Logger logger = Logger.getLogger(FileTool.class);

    private static final String FILE_TYPE_PROPERTIES_KEY = "fileTypePropertiesSource";
    private static final String DEFAULT_TYPE_ICON_KEY = "defaultTypeIcon";
    private Properties fileTypeProperties;
    private java.util.Map params;

    public void configure(java.util.Map params) {
        this.params = params;

        //load file type properties map
        String fileTypePropertiesSource = (String) params.get(FILE_TYPE_PROPERTIES_KEY);
        if (fileTypePropertiesSource != null) {
            fileTypeProperties = new Properties();
            ClassPathResource resource = new ClassPathResource(fileTypePropertiesSource);
            try {
                fileTypeProperties.load(resource.getInputStream());
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    public String getFileTypeIcon(String filename) {
        String key = StringUtils.lowerCase(FilenameUtils.getExtension(filename));
        if (key == null)
            return (String) params.get(DEFAULT_TYPE_ICON_KEY);

        String icon = (String) fileTypeProperties.get(key);
        return (String) (icon == null ? params.get(DEFAULT_TYPE_ICON_KEY) : icon);
    }

}
