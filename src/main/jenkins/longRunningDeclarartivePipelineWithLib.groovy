#!groovy
library 'testlib-functions'
def SCRIPT_STASH_NAME = "scripts"
pipeline {
    options {
        preserveStashes(buildCount: 2)
    }
    agent any
    stages {
        stage('Executing Long Running State') { // for display purposes
            stash name: SCRIPT_STASH_NAME , includes: "${WORKSPACE}/src/test/ruby/*"
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
                    functions.waitForRabbit(SCRIPT_STASH_NAME)
                }
            }
        }
        stage('Finishing up') {
            steps {
                script {
                    shoutOut "Howdy from a Declarative Pipeline"
                }
            }
        }
    }
}
