
def helloWorld() {
    echo "Hello World"
}

def approve() {
    userInput = input (id:"BuildOk" , message:"Ok for Build?" , submitter: 'svcjenkinsclient')
}

def buildSome(aBuildDir) {
    echo "Building $aBuildDir"
    dir("$aBuildDir") {
        withMaven( maven: 'maven') { sh "mvn clean install" }
    }
}

def waitForRabbit(stashName) {
    echo "Waiting for Input"
    unstash stashName
    def recievedStatus = sh(returnStatus: true, script: "input.rb")
    if (recievedStatus != 0) {
        error"Waiting for Input terminated with ${recievedStatus}"
    }
    println "Input Script status: $recievedStatus"
}