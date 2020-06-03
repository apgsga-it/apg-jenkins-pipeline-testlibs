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
        stage("(A) Ok") {
            steps {
                script {
                    unstash("Errorscript")
                    sh "chmod u+x error.pl"
                    sh "./error.pl -t Sometext"
                }
            }
        }
        stage("(B) Not Ok, without errorhandling") {
            steps {
                script {
                    unstash("Errorscript")
                    sh "chmod u+x error.pl"
                    if (params.skipB) {
                        echo "Skipping Test"
                    } else {
                        sh "./error.pl -t SomeErrorText -e"
                    }

                }
            }
        }
        stage("(C) Not Ok, with try catch block, continue") {
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
        stage("(D) Not Ok, with returnStatus, continue") {
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
        stage("(E) Not Ok, with returnStandard Output, fails") {
            steps {
                script {
                    unstash("Errorscript")
                    sh "chmod u+x error.pl"
                    if (params.skipE) {
                        echo "Skipping Test"
                    } else {
                        def returnStd = sh returnStdout: true, script: "./error.pl -t SomeYetOtherErrorText -e"
                        echo "Never get's to here: $returnStd"                    }
                }
            }
        }
        stage("(F) Not Ok, with returnStandard Output, try catch") {
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