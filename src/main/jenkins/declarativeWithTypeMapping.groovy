#!groovy
library 'testlib-functions'
def targetSystemMappingFile = libraryResource("TargetSystemMapping.json")
def targetSystemMap = loadTargetsMap(targetSystemMappingFile)
println(targetSystemMap)