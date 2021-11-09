package dymanic.bean.registration.buildingblocks

import io.temporal.activity.ActivityInterface
import io.temporal.workflow.WorkflowInterface
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner
import org.springframework.core.type.filter.AssignableTypeFilter

class DefinitionsScanner {

    static Reflections reflections = new Reflections("dymanic.bean.registration")

    static Set<BeanDefinitionWrapper> scanActivities(BeanDefinitionRegistry registry) {
        return scanSubclassesOfAnnotatedWith(registry, ActivityInterface.class)
    }

    static Set<BeanDefinitionWrapper> scanWorkflows(BeanDefinitionRegistry registry) {
        return scanSubclassesOfAnnotatedWith(registry, WorkflowInterface.class)
    }

    static Set<BeanDefinitionWrapper> scanSubclassesOfAnnotatedWith(BeanDefinitionRegistry registry, Class<?> annotation) {
        Set<Class<?>> interfaces = reflections.get(Scanners.TypesAnnotated.with(annotation).asClass())

        ClassPathBeanDefinitionScanner pathScanner = new ClassPathBeanDefinitionScanner(registry, false)
        interfaces.each { Class<?> type -> pathScanner.addIncludeFilter(new AssignableTypeFilter(type)) }

        return pathScanner
                .findCandidateComponents("dymanic.bean.registration.forscanning.firstcase")
                .collect { BeanDefinitionWrapper.wrap(it) }
                .toSet()
    }

}
