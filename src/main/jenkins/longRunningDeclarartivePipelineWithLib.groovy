#!groovy
library 'testlib-functions'
pipeline {
    agent any
    tools {
        maven 'apache-maven-3.6.3'
        jdk 'Jdk8'
    }
    stages {
        stage('Executing Long Running State') { // for display purposes
            steps {
                echo "checkout apg-gradle-plugins-testsmodules von git"
                git "https://github.com/apgsga-it/apg-gradle-plugins.git"
                functions.buildSome("integration/modules/testapp-bom")
                functions.buildSome("integration/modules/testapp-parentpom")
                functions.buildSome("integration/modules")
            }
        }
        stage('Waiting for some Input') {
            steps {
               functions.waitForRabbit()
            }
        }
        stage('Finishing up') {
            steps {
                echo "Howdy from a Declarative Pipeline"
            }
        }
    }
}
