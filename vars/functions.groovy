
def helloWorld() {
    echo "Hello World"
}

def approve() {
    userInput = input (id:"BuildOk" , message:"Ok for Build?" , submitter: 'svcjenkinsclient')
}