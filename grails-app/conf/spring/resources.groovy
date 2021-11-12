import dymanic.bean.registration.buildingblocks.WorkerBeansFactory
import dymanic.bean.registration.buildingblocks.WorkflowProvider
import dymanic.bean.registration.buildingblocks.WorkflowsFactory

// Place your Spring DSL code here
beans = {
    redisCacheService(WorkerBeansFactory) { bean ->
        bean.autowire = 'byName'
    }
    anotherServcdd(WorkflowProvider) { bean ->
        bean.autowire = 'byName'
    }
    specificFactory(WorkflowsFactory) { bean ->
        bean.autowire = 'byName'
    }
}
