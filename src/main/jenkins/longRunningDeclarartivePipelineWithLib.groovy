#!groovy
library 'testlib-functions'
pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'Jdk8'
    }
    stages {
        stage('Executing Long Running State') { // for display purposes
            steps {
                echo "checkout apg-gradle-plugins-testsmodules von git"
                git "https://github.com/apgsga-it/apg-gradle-plugins.git"
                script {
                    functions.buildSome("integration/modules/testapp-bom")
                    functions.buildSome("integration/modules/testapp-parentpom")
                    functions.buildSome("integration/modules")
                }
            }
        }
        stage('Waiting for some Input') {
            steps {
                script {
                    functions.waitForRabbit()
                }
            }
        }
        stage('Finishing up') {
            steps {
                script {
                    echo "Howdy from a Declarative Pipeline"
                }
            }
        }
    }
}
