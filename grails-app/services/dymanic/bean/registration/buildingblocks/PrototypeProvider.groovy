package dymanic.bean.registration.buildingblocks

class PrototypeProvider {

    static <T> T get(Class<T> clazz) {
        PrototypeFactory.springBeanFactory.getBean(clazz)
    }

}
