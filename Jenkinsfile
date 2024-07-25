pipeline {
    agent any
    environment {
        SONARQUBE_LOGIN = 'admin'
        SONARQUBE_PASSWORD = 'sonar'
        NEXUS_URL = 'http://localhost:8081/repository/maven-releases/'
        DOCKER_HUB_USER = 'alaabenjaber'
        DOCKER_HUB_PASSWORD = 'Zamzoom97***.'
    }

    stages {
        stage('GIT Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/alaabenjaber07/time-sheet-devops.git'
            }
        }
        stage('Build') {
            steps {
                script {
                    def mvnHome = tool 'M2_HOME'
                    sh "${mvnHome}/bin/mvn clean compile"
                }
            }
        }
        stage('MVN SONARQUBE') {
            steps {
                withSonarQubeEnv('SonarQube') { // 'SonarQube' est le nom de l'instance SonarQube configurée dans Jenkins
                    sh 'mvn sonar:sonar -Dsonar.login=$SONARQUBE_LOGIN -Dsonar.password=$SONARQUBE_PASSWORD'
                }
            }
        }
        stage('Test MOCKITO') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy to Nexus') {
            steps {
                script {
                    def mvnHome = tool 'M2_HOME'
                    sh "${mvnHome}/bin/mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::${env.NEXUS_URL}"
                }
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    // Construire le projet Maven
                    sh 'mvn clean package'
                    // Construire l'image Docker
                    sh 'docker build -t alaabenjaber/timesheet:1.0.0 .'
                }
            }
        }
        stage('Docker Push') {
            steps {
                script {
                    // Connexion à DockerHub
                    sh 'docker login -u $DOCKER_HUB_USER -p $DOCKER_HUB_PASSWORD'
                    sh 'docker push alaabenjaber/timesheet:1.0.0'
                }
            }
        }
         stage('Docker Compose Up') {
            steps {
                script {
                    // Démarrer les services définis dans docker-compose.yml
                    sh 'docker compose up -d'
                }
            }
         }
    }
}
