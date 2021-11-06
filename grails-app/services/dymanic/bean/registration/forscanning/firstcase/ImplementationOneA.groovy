package dymanic.bean.registration.forscanning.firstcase;

import dymanic.bean.registration.JustAService
import org.springframework.beans.factory.annotation.Autowired

class ImplementationOneA implements OneInterface {

    JustAService justAService

    def testMethod() {
        println("Checking Reference: ")
        println(this)
        println(justAService)
        println(justAService.hello())
        println(" ****************** ")
    }


}
