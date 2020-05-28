#!groovy
library 'testlib-functions'
def targetSystemMappingFile = libraryResource("var/TargetSystemMapping.json")
def targetSystemMap = functions.loadTargetsMap(targetSystemMappingFile)
println(targetSystemMap)