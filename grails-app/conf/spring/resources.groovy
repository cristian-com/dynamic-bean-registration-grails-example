import dymanic.bean.registration.buildingblocks.PrototypeFactory
import dymanic.bean.registration.buildingblocks.PrototypeProvider

// Place your Spring DSL code here
beans = {
    redisCacheService(PrototypeFactory) { bean ->
        bean.autowire = 'byName'
    }
    anotherServcdd(PrototypeProvider) { bean ->
        bean.autowire = 'byName'
    }
}
