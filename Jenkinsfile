#! /user/bin/env groovy

library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
    [$class: 'GitSCMSource',
    remote: 'https://github.com/salzaidy-devops/jenkins-shared-library.git',
    credentialsID: 'github-credentials'
    ]
)

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
                    // gv.testApp()
                }
            }
        }

        stage("increment build number") {
            steps {
                script {
                    echo 'Incrementing Gradle build version and preparing IMAGE_NAME...'
                    gv.setupGradleImageName()
                }
            }
        }


        stage("buildJarFile") {
            steps {
                script {
                    echo "Building jar file..."
                    buildGradleBootJarWithdot()
                }
            }
        }

        stage("buildDockerImage") {
            steps {
                script {
                    echo 'Building the application...'
                    echo 'Building Docker image...'
                    buildImage(env.IMAGE_NAME)
                    dockerLogin()
                    dockerPush(env.IMAGE_NAME)
                }
            }
        }

        stage("deployImageToAWS") {
            environment {
                AWS_ACCESS_KEY_ID = credentials('jenkins_aws_access_key_id')
                AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
            }
            steps {
                script{
                   echo 'Deploying the application...'
                   //  sh 'aws sts get-caller-identity'
                   // sh 'aws configure list'
                   sh 'envsubst < Kubernetes/deployment.yaml | kubectl apply -f -'
                   sh 'envsubst < Kubernetes/service.yaml | kubectl apply -f -'
                   // sh 'kubectl config get-contexts'
                   // sh 'kubectl config current-context'
                   // sh 'kubectl cluster-info'
                   // sh 'kubectl create deployment nginx-deployment --image=nginx'

                }
            }
        }
    }
}
