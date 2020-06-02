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
        timestamps()
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
                    // sh "./error.pl -t SomeErrorText -e"
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
        stage("Not Ok, with returnStatus") {
            steps {
                script {
                    unstash("Errorscript")
                    sh "chmod u+x error.pl"
                    def returnStatus = sh returnStatus: true, script: "./error.pl -t SomeOtherErrorText -e"
                    if (returnStatus != 0) {
                        echo "Error detected and WAT?????, still continueing"
                    }
                }
            }
        }
        stage("Not Ok, with returnStandard Output") {
            steps {
                script {
                    unstash("Errorscript")
                    sh "chmod u+x error.pl"
                  //  def returnStd = sh returnStdout: true, script: "./error.pl -t SomeYetOtherErrorText -e"
                    echo "Never get's to here: $returnStd"
                }
            }
        }
        stage("Not Ok, with returnStandard Output, try catch") {
            steps {
                script {
                    unstash("Errorscript")
                    sh "chmod u+x error.pl"
                    def returnStd = "Initial"
                    try {
                        returnStd = sh returnStdout: true, script: "./error.pl -t SomeYetOtherErrorText -e"
                    }
                    catch (err) {
                        echo err.getMessage()
                        echo "We got: ${returnStd} from standardout"
                    }
                }
            }
        }
    }
}