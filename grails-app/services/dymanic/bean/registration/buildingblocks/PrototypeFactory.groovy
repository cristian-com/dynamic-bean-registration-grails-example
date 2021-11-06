package dymanic.bean.registration.buildingblocks

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.config.RuntimeBeanReference
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor

import java.util.concurrent.ConcurrentHashMap

class PrototypeFactory implements BeanDefinitionRegistryPostProcessor {

    static ConfigurableBeanFactory springBeanFactory
    Map<Class, String> beans = new ConcurrentHashMap<>()

    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Set<BeanDefinition> types = DefinitionsScanner.scan(registry)

        types?.each { BeanDefinition beanDefinition ->
            String beanName = beanDefinition.getBeanClassName()
            beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE)

            if (registry.containsBeanDefinition("justAService")) {
                RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference("justAService")
                beanDefinition.getPropertyValues().addPropertyValue("justAService", runtimeBeanReference)
            }

            registry.registerBeanDefinition(beanName, beanDefinition)
            beans.put(beanDefinition.getClass(), beanName)
        }
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        springBeanFactory = beanFactory
    }

}
