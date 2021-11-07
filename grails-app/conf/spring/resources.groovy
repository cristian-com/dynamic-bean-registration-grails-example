import dymanic.bean.registration.buildingblocks.PrototypeBeansFactory
import dymanic.bean.registration.buildingblocks.PrototypeProvider
import dymanic.bean.registration.buildingblocks.SpecificFactory

// Place your Spring DSL code here
beans = {
    redisCacheService(PrototypeBeansFactory) { bean ->
        bean.autowire = 'byName'
    }
    anotherServcdd(PrototypeProvider) { bean ->
        bean.autowire = 'byName'
    }
    specificFactory(SpecificFactory) { bean ->
        bean.autowire = 'byName'
    }
}
