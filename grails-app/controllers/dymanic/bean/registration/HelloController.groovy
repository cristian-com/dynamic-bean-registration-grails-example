package dymanic.bean.registration

import dymanic.bean.registration.buildingblocks.WorkflowProvider
import dymanic.bean.registration.forscanning.firstcase.ImplementationOneA

class HelloController {

    def index() {
        ImplementationOneA implementationOneA = WorkflowProvider.get(ImplementationOneA.class)
        implementationOneA.testMethod()
    }

}
