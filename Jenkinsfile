pipeline {

    agent any

    tools {
        gradle 'gradle-8.12'
    }

    stages {
        stage("test") {
            steps {
                echo 'Testing the application...'
                sh 'gradle test'
            }
        }

        stage("build") {
            steps {
                echo 'Building the application...'
            }
        }

        stage("deploy") {
            steps {
                echo 'Deploying the application...'
            }
        }
    }
}