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

            // 1) Read and update version in build.gradle
            def gradleFile = readFile('build.gradle')

            // Extract current version: version = '0.0.1-SNAPSHOT'
            def versionMatcher = (gradleFile =~ /version\s*=\s*'(.+)'/)
            def version = versionMatcher ? versionMatcher[0][1] : "0.0.1-SNAPSHOT"
            echo "Raw version in build.gradle is: ${version}"

            // Remove -SNAPSHOT for numeric parsing
            def baseVersion = version.replace('-SNAPSHOT', '')
            def parts = baseVersion.tokenize('.')
            if (parts.size() != 3) {
                error "Version '${baseVersion}' is not in MAJOR.MINOR.PATCH format"
            }

            def major = parts[0].toInteger()
            def minor = parts[1].toInteger()
            def patch = parts[2].toInteger() + 1   // increment patch

            def newVersion = "${major}.${minor}.${patch}-SNAPSHOT"
            echo "New Gradle project version will be: ${newVersion}"

            // Replace version line in build.gradle
//             def updatedGradleFile = gradleFile.replaceFirst(/version\s*=\s*'.+'/, "version = '${newVersion}'")
//             writeFile file: 'build.gradle', text: updatedGradleFile

            // Clear version (for Docker image tag)
            def clearVersion = newVersion.replace('-SNAPSHOT', '')
            echo "Clear version (for image tag) is: ${clearVersion}"

            // 2) Read project name from settings.gradle (rootProject.name = '...')

            def settings = readFile('settings.gradle')
            def nameMatcher = (settings =~ /rootProject\.name\s*=\s*['"](.+)['"]/)
            def projectName = nameMatcher ? nameMatcher[0][1] : "unknown-project"
            echo "Project name from settings.gradle is: ${projectName}"

            // 3) Build IMAGE_NAME env var
            env.IMAGE_NAME = "salzaidy/${projectName}:${clearVersion}-${BUILD_NUMBER}"
            echo "IMAGE_NAME will be: ${env.IMAGE_NAME}"
        }
    }
}


        stage("buildJarFile") {
            steps {
                script {
                    echo "Building jar file..."
//                     gv.buildJarFile()
                    buildGradleBootJarWithdot()
                }

            }
        }

        stage("buildDockerImage") {
            steps {
                script {
                    echo 'Building the application...'
//                     gv.buildDockerImage()
                    echo 'Building Docker image...'
                    buildImage(env.IMAGE_NAME)
                    dockerLogin()
                    dockerPush(env.IMAGE_NAME)
                }
            }
        }

        stage("deployDockerImage") {
            steps {
                script{
                    echo 'Deploying the application...'
//                     gv.deployApp()
                }
            }
        }
    }
}
