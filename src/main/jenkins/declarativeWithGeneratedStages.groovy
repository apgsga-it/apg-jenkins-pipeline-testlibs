#!groovy
library 'testlib-functions'
def STASH_NAME = "modules"
pipeline {
    options {
        preserveStashes(buildCount: 2)
    }
    agent any
    stages {
        stage("Build's") {
            steps {
                echo "checkout apg-gradle-plugins-testsmodules von git"
                git "https://github.com/apgsga-it/apg-gradle-plugins.git"
                stash name: STASH_NAME , includes: "integration/modules/*"
                script {
                    functions.stage("Build testapp-bom", STASH_NAME,"integration/modules/testapp-bom", functions.&buildSomeFromStash)
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
