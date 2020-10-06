#!groovy
library 'testlib-functions'
def stageMappings = [ Entwickling:'CHEI212', Integration:'CHTI211']
def stages = stageMappings.keySet() as List
println(stageMappings)
println(stages)
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

                    stageMappings.each { target ->

                        stage("Approve ${target} Build") {
                            functions.doSomething(stageMappings(target))
                        }

                    }
                }
            }
        }
    }
}
