pipeline {
    agent any
    stages {
        stage('Executing Long Running State') { // for display purposes
            steps {
                echo "checkout apg-gradle-plugins-testsmodules von git"
                git "https://github.com/apgsga-it/apg-gradle-plugins.git"
                echo "Build Bom"
                dir("integration/modules/testapp-bom") {
                    sh "mvn clean install"
                }
                echo "Build Parent Pom"
                dir("integration/modules/testapp-parentpom") {
                    sh "mvn clean install"
                }
                echo "Build All"
                dir("integration/modules") {
                    sh "mvn clean install"
                }
                echo "Build Done"
            }
        }
        stage('Waiting for some Input') {
            steps {
                echo "Waiting for Input"
                timeout(time: 10, unit: 'DAYS') {
                    script {
                        recievedStatus = sh(returnStatus: true, script: "${WORKSPACE}/src/test/ruby/input.rb")
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
