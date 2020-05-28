
def helloWorld() {
    echo "Hello World"
}

def approve() {
    userInput = input (id:"BuildOk" , message:"Ok for Build?" )
}

def buildSome(aBuildDir) {
    echo "Building $aBuildDir"
    dir("$aBuildDir") {
        withMaven( maven: 'maven') { sh "mvn clean install" }
    }
}

def buildSomeFromStash(stashName, aBuildDir) {
    echo "Building $aBuildDir"
    unstash stashName
    dir("$aBuildDir") {
        withMaven( maven: 'maven') { sh "mvn clean install" }
    }
}

def waitForRabbit(stashName) {
    echo "Waiting for Input"
    unstash stashName
    def recievedStatus = sh(returnStatus: true, script: "src/test/ruby/input.rb")
    if (recievedStatus != 0) {
        error"Waiting for Input terminated with ${recievedStatus}"
    }
    println "Input Script status: $recievedStatus"
}

def stage(stageName, stashName, parameters, callback) {
    stage(stageName) {
        callback(stashName, parameters)
    }
}


def stagesConcurrent(stageName, stashName, parameters, callback) {
    def buildJobs = [:]
    for (int i = 0; i <= 5 ; i++) {
         buildJobs[jobNr-${i}] = {
             stage(stageName${i}) {
                 callback(stashName, parameters)
             }
         }
    }
    parallel buildJobs
}