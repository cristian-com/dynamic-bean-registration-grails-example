package dymanic.bean.registration.buildingblocks

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.config.RuntimeBeanReference
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.util.ClassUtils

import java.lang.reflect.Field
import java.util.concurrent.ConcurrentHashMap

class PrototypeBeansFactory implements BeanDefinitionRegistryPostProcessor {

    static ConfigurableBeanFactory springBeanFactory
    private final Map<Class, String> beans = new ConcurrentHashMap<>()

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        springBeanFactory = beanFactory
    }

    @Override
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Set<BeanDefinition> types = DefinitionsScanner.scan(registry)

        types.each { BeanDefinition beanDefinition ->
            String beanName = beanDefinition.getBeanClassName()
            beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE)
            addDependencies(beanDefinition, registry)

            beanDefinition.setFactoryBeanName("specificFactory")
            beanDefinition.setFactoryMethodName("newActivityStub")

            registry.registerBeanDefinition(beanName, beanDefinition)
            beans.put(beanDefinition.getClass(), beanName)
        }
    }

    private static void addDependencies(BeanDefinition beanDefinition,
                                        BeanDefinitionRegistry registry) {
        Class<?> clazz = ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), null)

        clazz.getDeclaredFields().each { Field field ->
            if (registry.containsBeanDefinition(field.getName())) {
                RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference(field.getName())
                beanDefinition.getPropertyValues().addPropertyValue(field.getName(), runtimeBeanReference)
            }
        }
    }

}
