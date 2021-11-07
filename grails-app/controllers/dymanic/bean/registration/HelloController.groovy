package dymanic.bean.registration

import dymanic.bean.registration.buildingblocks.PrototypeProvider
import dymanic.bean.registration.forscanning.firstcase.ImplementationOneA

class HelloController {

    def index() {
        ImplementationOneA implementationOneA = PrototypeProvider.get(ImplementationOneA.class)
        implementationOneA.testMethod()
    }

}
