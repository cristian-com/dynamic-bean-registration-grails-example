package dymanic.bean.registration.buildingblocks

class PrototypeProvider {

    static <T> T get(Class<T> clazz) {
       return PrototypeBeansFactory.springBeanFactory.getBean(clazz.getCanonicalName(),
               Collections.singleton(clazz)) as T
    }

}
