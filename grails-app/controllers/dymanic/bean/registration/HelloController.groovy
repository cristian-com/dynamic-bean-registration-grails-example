package dymanic.bean.registration

import dymanic.bean.registration.buildingblocks.PrototypeProvider
import dymanic.bean.registration.forscanning.firstcase.ImplementationOneA

class HelloController {

    PrototypeProvider prototypeProvider

    def index() {
        ImplementationOneA implementationOneA = PrototypeProvider.get(ImplementationOneA)
        implementationOneA.testMethod()
    }

}
