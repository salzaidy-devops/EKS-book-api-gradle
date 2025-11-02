def gv

pipeline {

    agent any

    tools {
        gradle 'gradle-8.12'
    }

    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("test") {
            steps {
                echo 'Testing the application...'
                gv.testApp()

            }
        }

        stage("build") {
            steps {
                echo 'Building the application...'
            }
        }

        stage("deploy") {
            steps {
                gv.deployApp()
//                 echo 'Deploying the application...'
            }
        }
    }
}