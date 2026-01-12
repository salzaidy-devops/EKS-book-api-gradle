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

    environment {
        DOCKER_ECR_REPO_SERVER = '915473859991.dkr.ecr.us-east-1.amazonaws.com'
        DOCKER_REGISTRY = 'ecr' //'dockerhub'   // or 'ecr'
        DOCKERHUB_REPO = 'salzaidy'
        ECR_REPO = "${env.DOCKER_ECR_REPO_SERVER}/java-gradle-apps"
        AWS_ACCESS_KEY_ID_CRED_ID = 'jenkins_aws_access_key_id'
        AWS_SECRET_ACCESS_KEY_CRED_ID = 'jenkins_aws_secret_access_key'
    }

    stages {
        stage("init") {
            steps {
                script {
                    echo "executing pipeline for branch $BRANCH_NAME"
                    gv = load 'script.groovy'
                }
            }
        }


        stage("test") {
            steps {
                script {
                    echo 'Testing the application...'
                    buildOps.test()
                }
            }
        }

        stage("increment build number") {
            steps {
                script {
                    echo 'Incrementing Gradle build version and preparing IMAGE_NAME...'
                    buildOps.bumpVersionAndPrepareImage()
                    echo "IMAGE_NAME from library: ${env.IMAGE_NAME}"
                }
            }
        }


        stage("buildJarFile") {
            steps {
                script {
                    echo "Building jar file..."
                    buildOps.buildJar()
                }
            }
        }

        stage("buildDockerImage") {
            steps {
                script {
                    echo 'Building Docker image for branch $BRANCH_NAME'
                    dockerOps.build(env.IMAGE_NAME)
                    dockerOps.login()
                    dockerOps.push(env.IMAGE_NAME)
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
                    sh 'kubectl config view --raw --minify | sed -n "/users:/,/contexts:/p"'
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
