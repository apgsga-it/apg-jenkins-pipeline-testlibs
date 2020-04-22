
def helloWorld() {
    echo "Hello World"
}

def approveBuild(patchConfig) {
    userInput = input (id:"BuildOk" , message:"Ok for Build?" , submitter: 'svcjenkinsclient')
}