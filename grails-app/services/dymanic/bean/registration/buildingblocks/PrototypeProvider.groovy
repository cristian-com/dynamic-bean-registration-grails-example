package dymanic.bean.registration.buildingblocks

class PrototypeProvider {

    static <T> T get(Class<T> clazz) {
       return PrototypeBeansFactory.springBeanFactory.getBean(clazz.getCanonicalName(),
               BeanArgsWrapper.of(clazz)) as T
    }

}
