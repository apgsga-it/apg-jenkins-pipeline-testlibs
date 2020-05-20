pipeline {
    agent any
    stages {
        stage('Executing Testcode') { // for display purposes
            steps {
                echo "Executeing a script , which uses rabbitmq"
                script {
                    outp = sh(returnStdout: true, script: "${WORKSPACE}/src/test/ruby/blabblr.rb").trim()
                    println "Script output: $outp"
                }
            }
        }
        stage('Waiting for some Input') {
            steps {
                echo "Waiting for Input
                script {
                    recievedStatus = sh(returnStatus: true, script: "${WORKSPACE}/src/test/ruby/input.rb")
                    println "Input Script status: $recievedStatus"
                }
            }
        }
        stage('Finishing up') {
            steps {
                echo "Oki what else"
            }
        }
    }
}
