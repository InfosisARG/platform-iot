package com.infosisarg;

import java.util.Properties;

import org.pf4j.PluginDescriptor;
import org.pf4j.PropertiesPluginDescriptorFinder;
import org.pf4j.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlatformPropertiesPluginDescriptorFinder extends PropertiesPluginDescriptorFinder{
	
	private final Logger logger = LoggerFactory.getLogger(PluginManagerHolder.class);

	@Override
	protected PlatformPluginDescriptor createPluginDescriptorInstance() {
		return new PlatformPluginDescriptor();
	}
	
	@Override
	protected PluginDescriptor createPluginDescriptor(Properties properties) {
        PlatformPluginDescriptor pluginDescriptor = createPluginDescriptorInstance();

        // TODO validate !!!
        String id = properties.getProperty("plugin.id");
        pluginDescriptor.setPluginId(id);

        String description = properties.getProperty("plugin.description");
        if (StringUtils.isNullOrEmpty(description)) {
            pluginDescriptor.setPluginDescription("");
        } else {
            pluginDescriptor.setPluginDescription(description);
        }

        String clazz = properties.getProperty("plugin.class");
        if (StringUtils.isNotNullOrEmpty(clazz)) {
            pluginDescriptor.setPluginClass(clazz);
        }

        String version = properties.getProperty("plugin.version");
        if (StringUtils.isNotNullOrEmpty(version)) {
            pluginDescriptor.setPluginVersion(version);
        }

        String provider = properties.getProperty("plugin.provider");
        pluginDescriptor.setProvider(provider);

        String dependencies = properties.getProperty("plugin.dependencies");
        pluginDescriptor.setDependencies(dependencies);

        String requires = properties.getProperty("plugin.requires");
        if (StringUtils.isNotNullOrEmpty(requires)) {
            pluginDescriptor.setRequires(requires);
        }
        
        String sendTopic = properties.getProperty("platform.sendTopic");
        if (StringUtils.isNotNullOrEmpty(sendTopic)) {
            pluginDescriptor.setSendTopic(sendTopic);
        } else {
        	logger.warn("Plugin: " + clazz + " not set platform.sendTopic");
        }
        
        String receiveTopics = properties.getProperty("platform.receiveTopics");
        if (StringUtils.isNotNullOrEmpty(receiveTopics)) {
            pluginDescriptor.setReceiveTopics(receiveTopics);
        } else {
        	logger.warn("Plugin: " + clazz + " platform.receiveTopics");
        }

        pluginDescriptor.setLicense(properties.getProperty("plugin.license"));

        return pluginDescriptor;
    }
	
	
}