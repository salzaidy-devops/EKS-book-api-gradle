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
                script {
                    echo 'Building the application...'
                    gv.buildApp
                }
            }
        }

        stage("deploy") {
            steps {
                script{
                    echo 'Deploying the application...'
                    gv.deployApp()
                }
            }
        }
    }
}