#!groovy
library 'testlib-functions'
def targetSystemMappingFile = libraryResource("TargetSystemMappings.json")
def targetSystemMap = functions.loadTargetsMap(targetSystemMappingFile)
println(targetSystemMap)