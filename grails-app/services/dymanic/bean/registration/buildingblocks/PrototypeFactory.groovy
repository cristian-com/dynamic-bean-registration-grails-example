package dymanic.bean.registration.buildingblocks

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor

class PrototypeFactory implements BeanDefinitionRegistryPostProcessor {

    static ConfigurableBeanFactory springBeanFactory

    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Set<BeanDefinition> types = DefinitionsScanner.scan(registry)

        types?.each { BeanDefinition beanDefinition ->
            String beanName = beanDefinition.getBeanClassName()
            beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE)
            registry.registerBeanDefinition(beanName, beanDefinition)
        }
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        springBeanFactory = beanFactory
    }

}
