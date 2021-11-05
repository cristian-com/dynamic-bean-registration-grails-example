package dymanic.bean.registration.forscanning.firstcase;

import dymanic.bean.registration.JustAService
import org.springframework.beans.factory.annotation.Autowired

class ImplementationOneA implements OneInterface {

    @Autowired
    JustAService justAService

    def testMethod() {
        println("Checking Reference: ")
        println(this)
        println(justAService)
        println(" ****************** ")
    }


}
