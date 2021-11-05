package dymanic.bean.registration.buildingblocks

import dymanic.bean.registration.forscanning.TheAnnotation
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner
import org.springframework.core.type.filter.AssignableTypeFilter

class DefinitionsScanner {

    static Reflections reflections = new Reflections("dymanic.bean.registration")

    static Set<BeanDefinition> scan(BeanDefinitionRegistry registry) {
        Set<Class<?>> interfaces = reflections.get(Scanners.TypesAnnotated.with(TheAnnotation.class).asClass())

        ClassPathBeanDefinitionScanner pathScanner = new ClassPathBeanDefinitionScanner(registry)
        interfaces.each { Class<?> type -> pathScanner.addIncludeFilter(new AssignableTypeFilter(type)) }

        return pathScanner.findCandidateComponents("dymanic.bean.registration.forscanning.firstcase")
    }

}
