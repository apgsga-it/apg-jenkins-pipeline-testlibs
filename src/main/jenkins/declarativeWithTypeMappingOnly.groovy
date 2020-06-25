#!groovy
library 'testlib-functions'
java.lang.String Entwicklung = "Entwicklung"
def targetSystemMappingFile = libraryResource("TargetSystemMappings.json")
println(targetSystemMappingFile)
def targetSystemMap = functions.loadTargetsMap(targetSystemMappingFile)
println(targetSystemMap)
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
                println(targetSystemMap)
            }
        }
    }
}