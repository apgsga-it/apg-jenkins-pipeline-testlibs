pipeline {
    agent any
    stages {
        stage('Getting some Testcode') { // for display purposes
            steps {
                echo "Getting some test code"
                git branch: 'IT-35749-Discussions', url: 'https://github.com/apgsga-it/apg-jenkins-pipelines.git'
            }
        }
        stage('Executing Testcode') { // for display purposes
            steps {
                echo "Executeing a script , which uses rabbitmq"
                script {
                    outp = sh(returnStdout: true, script: "src/test/ruby/blabblr.rb").trim()
                    println "Script output: $outp"
                }
            }
        }
        stage('Waiting for some Input') {
            steps {
                echo "Waiting for Input"
                timeout(time: 10, unit: 'DAYS') {
                    script {
                        recievedStatus = sh(returnStatus: true, script: "src/test/ruby/input.rb")
                        println "Input Script status: $recievedStatus"
                    }
                }
            }
        }
        stage('Waiting some more') {
            steps {
                echo "Waiting for some more Input"
                timeout(time: 10, unit: 'DAYS') {
                    script {
                        recievedStatus = sh(returnStatus: true, script: "src/test/ruby/input.rb")
                        println "Input Script status: $recievedStatus"
                    }
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
