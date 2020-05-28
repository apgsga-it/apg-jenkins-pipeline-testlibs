#!groovy
library 'testlib-functions'
def targetSystemMappingFile = libraryResource("src/main/TargetSystemMappings.json")
def targetSystemMap = functions.loadTargetsMap(targetSystemMappingFile)
println(targetSystemMap)