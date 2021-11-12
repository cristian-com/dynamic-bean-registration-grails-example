package dymanic.bean.registration.buildingblocks

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.config.RuntimeBeanReference
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor

import java.lang.reflect.Field

class WorkerBeansFactory implements BeanDefinitionRegistryPostProcessor {

    ConfigurableBeanFactory springBeanFactory
    WorkflowRepository workflowRepository

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        springBeanFactory = beanFactory
    }

    @Override
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Set<BeanDefinitionWrapper> workflows = BeanImplementationsScanner.scanWorkflows(registry)
        Set<BeanDefinitionWrapper> activities = BeanImplementationsScanner.scanActivities(registry)

        registerWorkflows(workflows, registry)
        registerActivities(activities, registry)
    }

    private void registerWorkflows(Set<BeanDefinitionWrapper> workflows, BeanDefinitionRegistry registry) {
        registerBeans(workflows, registry, "specificFactory", WorkflowsFactory.METHOD_FACTORY_NAME)
        workflows.each { BeanDefinitionWrapper it -> workflowRepository.addWorkflow(it) }
    }

    private void registerActivities(Set<BeanDefinitionWrapper> activities, BeanDefinitionRegistry registry) {
        registerBeans(activities, registry, "specificFactory", WorkflowsFactory.METHOD_FACTORY_NAME)
        activities.each { BeanDefinitionWrapper it -> workflowRepository.addActivity(it) }
    }

    private static Set<BeanDefinitionWrapper> registerBeans(Set<BeanDefinitionWrapper> beanDefinitions,
                                                    BeanDefinitionRegistry registry, String factoryName,
                                                    String factoryMethod) {
        beanDefinitions.each { BeanDefinitionWrapper beanDefinitionWrapper ->
            String beanName = beanDefinitionWrapper.getBeanClassName()
            BeanDefinition beanDefinition = getConfiguredBeanDefinition(beanDefinitionWrapper, registry,
                    factoryName, factoryMethod)
            registry.registerBeanDefinition(beanName, beanDefinition)
        }
    }

    private static BeanDefinition getConfiguredBeanDefinition(BeanDefinitionWrapper beanDefinitionWrapper,
                                                              BeanDefinitionRegistry registry, String factoryName,
                                                              String factoryMethod) {
        beanDefinitionWrapper.setScope(BeanDefinition.SCOPE_PROTOTYPE)
        beanDefinitionWrapper.setFactoryBeanName(factoryName)
        beanDefinitionWrapper.setFactoryMethodName(factoryMethod)
        beanDefinitionWrapper.setLazyInit(false)

        Map<String, RuntimeBeanReference> dependencies = getDependencies(beanDefinitionWrapper.resolveBeanClass(), registry)

        dependencies.each { String dependencyName, RuntimeBeanReference beanReference ->
            beanDefinitionWrapper.getPropertyValues().addPropertyValue(dependencyName, beanReference)
        }

        return beanDefinitionWrapper.beanDefinition
    }

    private static Map<String, RuntimeBeanReference> getDependencies(Class<?> target, BeanDefinitionRegistry beansRegistry) {
        Map<String, RuntimeBeanReference> dependencies = new HashMap<>()

        target.getDeclaredFields().each { Field field ->
            if (beansRegistry.containsBeanDefinition(field.getName())) {
                dependencies.put(field.getName(), new RuntimeBeanReference(field.getName()))
            }
        }

        return dependencies
    }

}
