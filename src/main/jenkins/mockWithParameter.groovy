#!groovy
pipeline {
    agent any
    parameters {
        string(name: 'TARGET', defaultValue: '')
        string(name: 'PARAMETER', defaultValue: '')
    }
    stages {
        stage('Example Pipeline') {
            steps {
                echo "Running for ${params.TARGET} with ${params.PARAMETER}"
            }
        }
    }
}