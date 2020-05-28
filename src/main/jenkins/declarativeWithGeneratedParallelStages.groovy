#!groovy
library 'testlib-functions'
def STASH_NAME = "modules"
pipeline {
    options {
        preserveStashes(buildCount: 2)
    }
    agent any
    stages {
        stage("Prepare and Generate Builds Stages") {
            steps {
                echo "checkout apg-gradle-plugins-testsmodules von git"
                git "https://github.com/apgsga-it/apg-gradle-plugins.git"
                stash name: STASH_NAME , includes: "integration/modules/*"
                script {
                    functions.stage("Build testapp-bom", STASH_NAME,"integration/modules/testapp-bom", functions.&buildSomeFromStash)
                    functions.stage("Build testapp-parentpom" , STASH_NAME,"integration/modules/testapp-parentpom",  functions.&buildSomeFromStash)
                    functions.stage("Build modules" , STASH_NAME,"integration/modules/testapp-module",  functions.&stagesConcurrenFromStash)
                    functions.stage("Build service" , STASH_NAME,"integration/modules/testapp-service",  functions.&buildSomeFromStash)
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
