#!groovy
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh "./mvnw clean package"
                sh "docker build . -t austinbaugh/utopia-flights-microservice:${env.BUILD_ID}"
            }
        }
    }
}
