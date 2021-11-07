package dymanic.bean.registration.buildingblocks

class SpecificFactory {

    def <T> T newActivityStub(Set<Object> params) {
        Class<T> clazz = params.first() as Class<T>
        return clazz.newInstance()
    }

}
