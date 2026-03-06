pipeline {
    agent any

    tools {
        jdk 'system-jdk21'
        nodejs 'React'
    }

    environment {
        BACK_HOST        = "172.20.3.10"
        BACK_USER        = "fitness"
        BACK_PATH        = "/opt/backend-dev/app.jar"
        BACK_CREDENTIALS = "ssh-back-dev"

        FRONT_HOST        = "172.20.3.20"
        FRONT_USER        = "fitness"
        FRONT_PATH        = "/var/www/html/my-app"
        FRONT_CREDENTIALS = "ssh-front-dev"

        DB_HOST        = "172.20.3.30"
        DB_USER        = "fitness"
        DB_PATH        = "/opt/db-dev"
        DB_CREDENTIALS = "ssh-db-dev"
    }

    stages {

        stage('Clone') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/GNABARR/fitness-goal-road-.git'
            }
        }

        stage('Build Backend') {
            steps {
                dir('backend') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Deploy Backend') {
            steps {
                script {
                    def jarFile = sh(script: "ls backend/target/*.jar | grep -v original | head -1", returnStdout: true).trim()
                    echo "JAR: ${jarFile}"
                    sshagent(credentials: [env.BACK_CREDENTIALS]) {
                        sh """
                            scp -o StrictHostKeyChecking=no ${jarFile} \
                                ${BACK_USER}@${BACK_HOST}:${BACK_PATH}
                        """
                    }
                }
            }
        }

        stage('Restart Backend Service') {
            steps {
                sshagent(credentials: [env.BACK_CREDENTIALS]) {
                    sh """
                        ssh -o StrictHostKeyChecking=no ${BACK_USER}@${BACK_HOST} \
                            "sudo systemctl restart backend-dev"
                    """
                }
            }
        }

        stage('Deploy Frontend') {
            steps {
                script {
                    def buildDir = sh(script: "[ -d frontend/dist ] && echo 'frontend/dist' || echo 'frontend/build'", returnStdout: true).trim()
                    echo "Build dir: ${buildDir}"
                    sshagent(credentials: [env.FRONT_CREDENTIALS]) {
                        sh """
                            ssh -o StrictHostKeyChecking=no ${FRONT_USER}@${FRONT_HOST} \
                                "rm -rf ${FRONT_PATH}/*"
                            scp -o StrictHostKeyChecking=no -r ${buildDir}/* \
                                ${FRONT_USER}@${FRONT_HOST}:${FRONT_PATH}
                        """
                    }
                }
            }
        }

        stage('Deploy Nginx Config') {
    steps {
        sshagent(credentials: [env.FRONT_CREDENTIALS]) {
            sh """
                scp -o StrictHostKeyChecking=no nginx.conf \
                    ${FRONT_USER}@${FRONT_HOST}:/tmp/nginx.conf

                ssh -o StrictHostKeyChecking=no ${FRONT_USER}@${FRONT_HOST} \
                    "sudo mv /tmp/nginx.conf /etc/nginx/sites-enabled/default"
            """
        }
    }
}

        stage('Reload Nginx') {
            steps {
                sshagent(credentials: [env.FRONT_CREDENTIALS]) {
                    sh """
                        ssh -o StrictHostKeyChecking=no ${FRONT_USER}@${FRONT_HOST} \
                            "sudo systemctl reload nginx"
                    """
                }
            }
        }

        stage('Deploy Database') {
            steps {
                script {
                    def composeFile = sh(script: "[ -f docker-compose.yml ] && echo 'docker-compose.yml' || echo 'docker-compose.yaml'", returnStdout: true).trim()
                    echo "Compose file: ${composeFile}"
                    sshagent(credentials: [env.DB_CREDENTIALS]) {
                        sh """
                            ssh -o StrictHostKeyChecking=no ${DB_USER}@${DB_HOST} \
                                "mkdir -p ${DB_PATH}"
                            scp -o StrictHostKeyChecking=no ${composeFile} \
                                ${DB_USER}@${DB_HOST}:${DB_PATH}/docker-compose.yml
                        """
                    }
                }
            }
        }

        stage('Start Database Container') {
            steps {
                sshagent(credentials: [env.DB_CREDENTIALS]) {
                    sh """
                        ssh -o StrictHostKeyChecking=no ${DB_USER}@${DB_HOST} \
                            "cd ${DB_PATH} && docker compose pull && docker compose up -d --remove-orphans"
                    """
                }
            }
        }
    }

    post {
        success {
            echo "aaaaaal it gooood"
        }
        failure {
            echo "madre mia, more work"
        }
    }
}