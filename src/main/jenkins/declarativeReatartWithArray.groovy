#!groovy
library 'testlib-functions'
def stageMappings = [ Entwickling:'CHEI212', Integration:'CHTI211']
pipeline {
    options {
        preserveStashes(buildCount: 2)
        timestamps()
    }
    agent any
    stages {
        stage('Init') {
            steps {
                script {
                    stageMappings.each { stageName ->
                        stage("Approve ${stageName} Build") {
                            functions.doSomething(stageMappings(stageName))
                        }

                    }
                    println "Do something"
                }
            }
        }
    }
}
