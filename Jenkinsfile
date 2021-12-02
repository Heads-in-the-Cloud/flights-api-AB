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

        stage('Run detached for 45sec') {
            steps {
                sh """
                    docker run -d \
                        --rm \
                        --name flights-microservice \
                        --env DB_URL=${env.DB_URL} \
                        --env DB_USERNAME=${env.DB_USERNAME} \
                        --env DB_PASSWORD=${env.DB_PASSWORD} \
                        --env JWT_SECRET=${env.JWT_SECRET} \
                        -p 8100:8080 \
                        austinbaugh/utopia-flights-microservice:${env.BUILD_ID}
                """
                sh "sleep 45"
            }
        }

        stage('Test') {
            steps {
                sh "./test.sh 8100"
            }
        }

        stage('Kill') {
            steps {
                sh "docker kill flights-microservice"
            }
        }
    }
}

