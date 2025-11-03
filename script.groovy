def testApp() {
    echo "testing the application..."
    sh "gradle test"
    echo "executing pipeline for branch $BRANCH_NAME"
}


def buildJarFile() {
    sh 'gradle clean'
    sh './gradlew bootJar'
}

def buildDockerImage() {
    echo "building docker image"
    sh 'docker build -t salzaidy/book-api:1.0 .'
}



def deployApp() {
    echo "deploying the application"
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh 'docker push salzaidy/book-api:1.0'
    }
}

return this