node {
    stage('Executing Long Running State') { // for display purposes
        echo "checkout apg-gradle-plugins-testsmodules von git"
        git "https://github.com/apgsga-it/apg-gradle-plugins.git"
        echo "Build Bom"
        def mvCommand = "mvn clean install"
        dir("integration/modules/testapp-bom") {
            withMaven( maven: 'maven') { sh "${mvCommand}" }
        }
        echo "Build Parent Pom"
        dir("integration/modules/testapp-parentpom") {
            withMaven( maven: 'maven') { sh "${mvCommand}" }
        }
        echo "Build All"
        dir("integration/modules") {
            withMaven( maven: 'maven') { sh "${mvCommand}" }
        }
        echo "Build Done"
    }
    stage('Waiting for some Input') {
        echo "Getting some Ruby script"
        git branch: 'IT-35749-Discussions', url: 'https://github.com/apgsga-it/apg-jenkins-pipelines.git'
        echo "Waiting for Input"
        def recievedStatus = sh(returnStatus: true, script: "${WORKSPACE}/src/test/ruby/input.rb")
        echo "Input Script status: $recievedStatus"

    }
    stage('Finishing up') {
        echo "Oki what else"
    }
}
