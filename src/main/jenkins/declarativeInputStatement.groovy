#!groovy
library 'testlib-functions'
def Entwicklung = "Entwicklung"
def targetSystemMap = ["$Entwicklung":"CHEI212"]
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