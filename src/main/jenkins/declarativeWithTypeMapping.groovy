#!groovy
library 'testlib-functions'
def targetSystemMappingFile = libraryResource("TargetSystemMapping.json")
def targetSystemMap = functions.loadTargetsMap(targetSystemMappingFile)
println(targetSystemMap)