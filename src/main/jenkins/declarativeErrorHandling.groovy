#!groovy
library 'testlib-functions'
java.lang.String Entwicklung = "Entwicklung"
node {
    stash name: "Errorscript", includes: "src/main/error.pl"
}
pipeline {
    options {
        preserveStashes(buildCount: 2)
        timestamps ()
    }
    agent any
    stages {
        stage(Entwicklung) {
            steps {
                script {
                    unstash("Errorscript")
                    sh "chmod u+x error.pl"
                    sh "./error.pl"
                }
            }
        }
    }
}