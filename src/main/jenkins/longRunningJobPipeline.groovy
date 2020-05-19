pipeline {
    agent any
    stages {
        stage('Executing Long Running State') { // for display purposes
            steps {
                echo "checkout apg-gradle-plugins-testsmodules von HEAD"
                sh "rm -Rf apg-gradle-plugins-testsmodules"
                sh "cvs -d${env.CVS_ROOT} co apg-gradle-plugins-testsmodules"
                echo "Build Bom"
                dir ("apg-gradle-plugins-testsmodules/testapp/testapp-bom") {
                    withMaven( maven: 'apache-maven-3.5.0') { sh "mvn clean install" }
                }
                echo "Build Parent Pom"
                dir ("apg-gradle-plugins-testsmodules/testapp/testapp/testapp-parentpom") {
                    withMaven( maven: 'apache-maven-3.5.0') { sh "mvn clean install" }
                }
                echo "Build All"
                dir ("apg-gradle-plugins-testsmodules/testapp/testapp") {
                    withMaven( maven: 'apache-maven-3.5.0') { sh "mvn clean install" }
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
