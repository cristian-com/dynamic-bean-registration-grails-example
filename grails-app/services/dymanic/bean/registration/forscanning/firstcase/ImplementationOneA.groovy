package dymanic.bean.registration.forscanning.firstcase

import dymanic.bean.registration.Hola;
import dymanic.bean.registration.JustAService
import groovy.util.logging.Log4j

@Log4j
class ImplementationOneA implements OneInterface {

    JustAService justAService

    def testMethod() {

        Hola.withTransaction { x ->
            println(x)
        }

        println("Checking Reference: ")
        println(this)
        println(justAService)
        println(justAService.hello())
        println(" ****************** ")
        log.debug("Hello world")
    }


}
