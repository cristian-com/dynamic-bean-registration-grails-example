package dymanic.bean.registration

import grails.core.GrailsApplication

class JustAService {

    GrailsApplication grailsApplication

    def hello() {
        print(grailsApplication)
        log.debug("Hello world")
    }

}
