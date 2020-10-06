#!groovy
library 'testlib-functions'
def stageMappings = [ Entwickling:'CHEI212', Integration:'CHTI211']
def stages = stageMappings.keySet() as List
pipeline {
    options {
        preserveStashes(buildCount: 2)
        timestamps ()
    }
    agent any
    stages {
        stage(Entwicklung) {
            steps {
                echo "Building for Target: ${targetSystemMap[Entwicklung]}"
                script {
                    functions.approve()
                }
            }
        }
    }
}
