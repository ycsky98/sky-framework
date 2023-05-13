package org.sky.framework.config.yaml;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Properties;

/**
 * @author yangcong
 *
 * springboot在处理读取自定义yaml文件是未提供接口的(springboot在读取只默认读取application.yaml,需要配置工厂)
 * 需要重写工厂
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) {

        Resource resource = encodedResource.getResource();

        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource);

        Properties props = factory.getObject();

        return new PropertiesPropertySource(resource.getFilename(), props);
    }
}
