#!groovy
library 'testlib-functions'
java.lang.String Entwicklung = "Entwicklung"
def errorPerlScript = libraryResource("error.pl")
node {
    writeFile file: "error.pl", text: errorPerlScript
    stash name: "Errorscript", includes: "error.pl"
}
pipeline {
    options {
        preserveStashes(buildCount: 2)
        timestamps ()
    }
    agent any
    stages {
        stage(Entwicklung) {
            steps {
                script {
                    unstash("Errorscript")
                    sh "cat error.pl"
                    sh "chmod u+x error.pl"
                }
            }
        }
    }
}