#!groovy
library 'testlib-functions'
java.lang.String Entwicklung = "Entwicklung"
def errorPerlScript = libraryResource("TargetSystemMappings.json")
writeFile file: "error.pl", text: errorPerlScript
stash "Errorscript", "error.pl"
pipeline {
    options {
        preserveStashes(buildCount: 2)
        timestamps ()
    }
    agent any
    stages {
        stage(Entwicklung) {
            steps {
               echo "Building for Target: ${targetSystemMap[Entwicklung].targetName}"
                script {
                    unstash("Errorscript")
                    sh "error.pl"
                }
            }
        }
    }
}