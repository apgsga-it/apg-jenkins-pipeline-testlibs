#!groovy
library 'testlib-functions'
java.lang.String Entwicklung = "Entwicklung"
def targetSystemMappingFile = libraryResource("TargetSystemMappings.json")
println(targetSystemMappingFile)
def targetSystemMap = functions.loadTargetsMap(targetSystemMappingFile)
println(targetSystemMap)
node {
    def file_in_workspace = unstashFileParameter "patchFile.json"
    fileOperations([fileDeleteOperation(includes: 'PatchFile.json')])
    fileOperations([fileRenameOperation(source: "${file_in_workspace}",  destination: 'PatchFile.json')])
    sh "cat PatchFile.json"
    stash name: "PatchFile" , includes:  'PatchFile.json'
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
               echo "Building for Target: ${targetSystemMap[Entwicklung].targetName}"
                script {
                    unstash("PatchFile")
                    sh "cat PatchFile.json"
                }
            }
        }
    }
}
