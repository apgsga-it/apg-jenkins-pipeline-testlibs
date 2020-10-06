#!groovy
library 'testlib-functions'
def stageMappings = [ Entwickling:'CHEI212', Integration:'CHTI211']
def stageNames = stageMappings.keySet()
pipeline {
    options {
        preserveStashes(buildCount: 2)
        timestamps()
    }
    agent any
    stages {
        stage('Stages Generation') {
            steps {
                script {
                    stageNames.each { stageName ->
                        stage("Approve ${stageName} Build") {
                            functions.approveTarget(stageMappings[stageName])
                        }
                        stage("Build  for ${stageName} ") {
                            functions.buildSomething(stageMappings[stageName])
                        }
                    }
                }
            }
        }
    }
}
