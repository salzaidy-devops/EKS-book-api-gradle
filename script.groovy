def testApp() {
    echo "testing the application..."
    sh "gradle test"
    echo "executing pipeline for branch $BRANCH_NAME"
}

def deployApp() {
    echo "deploying the application"
}

return this