#!groovy
library 'testlib-functions'
def targetSystemMappingFile = libraryResource("TargetSystemMappings.json")
println(targetSystemMappingFile)
def targetSystemMap = functions.loadTargetsMap(targetSystemMappingFile)
println(targetSystemMap)