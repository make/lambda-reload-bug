package lambda.reload.bug

import crash.Bug

class BootStrap {

    def init = { servletContext ->
        Bug.crash()
    }
    def destroy = {
    }
}
