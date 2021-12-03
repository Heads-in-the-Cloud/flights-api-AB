#!groovy
pipeline {
    agent any

    environment {
        image_label = "austinbaugh/utopia-flights-microservice"
        git_commit_hash ="${sh(returnStdout: true, script: 'git rev-parse HEAD')}"
        image = ""
    }

    stages {
        stage('Package') {
            steps {
                sh "./mvnw package"
            }
        }

        stage('Build') {
            steps {
                script {
                    image = docker.build("$image_label:$git_commit_hash")
                }
            }
        }

        stage('Push to registry') {
            steps {
                script {
                    docker.withRegistry('', 'registry-creds') {
                        image.push()
                        image.push('latest')
                    }
                }
            }
        }

        stage('Clean up') {
            sh "./mvwn clean"
            sh "docker rmi $image_label:$git_commit_hash"
        }
    }
}
