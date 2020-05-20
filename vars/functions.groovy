
def helloWorld() {
    echo "Hello World"
}

def approve() {
    userInput = input (id:"BuildOk" , message:"Ok for Build?" , submitter: 'svcjenkinsclient')
}

def buildSome(aBuildDir) {
    echo "Building $aBuildDir"
    dir("$aBuildDir") {
        withMaven( maven: 'maven') { sh "${mvCommand}" }
    }
}

def waitForRabbit() {
    echo "Waiting for Input"
    def recievedStatus = sh(returnStatus: true, script: "${WORKSPACE}/src/test/ruby/input.rb")
    if (recievedStatus != 0) {
        error"Waiting for Input terminated with ${recievedStatus}"
    }
    println "Input Script status: $recievedStatus"
}