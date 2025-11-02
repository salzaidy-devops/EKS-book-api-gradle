#! /user/bin/env groovy

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
                    gv = load 'script.groovy'
                }
            }
        }
        stage("test") {
            steps {
                script {
                    echo 'Testing the application...'
                    gv.testApp()
                }
            }
        }

        stage("build") {
            steps {
                echo 'Building the application...'
            }
        }

        stage("deploy") {
            steps {
                script{
                    gv.deployApp()
                }
//                 echo 'Deploying the application...'
            }
        }
    }
}