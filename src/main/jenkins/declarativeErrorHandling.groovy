#!groovy
library 'testlib-functions'
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
        stage("Ok") {
            steps {
                script {
                    unstash("Errorscript")
                    sh "chmod u+x error.pl"
                    sh "./error.pl -t Sometext"
                }
            }
        }
        stage("Not Ok, without errorhandling") {
            steps {
                script {
                    unstash("Errorscript")
                    sh "chmod u+x error.pl"
                    sh "./error.pl -t SomeErrorText -e"
                }
            }
        }
        stage("Not Ok, with try catch block, continue") {
            steps {
                script {
                    unstash("Errorscript")
                    sh "chmod u+x error.pl"
                    try {
                        sh "./error.pl -t SomeOtherErrorText -e"
                    } catch (err) {
                        echo err.getMessage()
                        echo "Error detected, but we will continue."
                    }
                }
            }
        }
    }
}