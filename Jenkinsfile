pipeline {
  agent any
  tools { 
      maven 'MAVEN_HOME' 
      jdk 'JAVA_HOME'
    }

  environment {
    KUBECONFIG = '/home/infl302/.kube/config'
  }
  stages
  {
    stage('SonarQube'){
      steps {
        sh "mvn verify sonar:sonar -Dsonar.login='admin' -Dsonar.password='Password@123' -Dsonar.token='ea304a848a909935874d4d85ea78cff50e8aff09'"
      }
    }
    
    stage('Build') {
      steps {
        sh "mvn verify sonar:sonar -Dsonar.login='admin' -Dsonar.password='Password@123' -Dsonar.token='ea304a848a909935874d4d85ea78cff50e8aff09'"
        sh 'docker build -t localhost:5000/car:latest .'
        sh 'docker push localhost:5000/car:latest'
      }
    }

    stage('Deploy') {
      steps {
        sh 'curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl'
        sh 'chmod +x kubectl'
        sh 'sudo mv kubectl /usr/local/bin/'
        sh 'kubectl version --client'

        sh 'kubectl apply -f mongo-deployment.yaml'
        sh 'kubectl apply -f influxdb-deployment.yaml'
        sh 'kubectl apply -f javacontainer-deployment.yaml'
        sh 'kubectl apply -f services.yaml'
      }
    }
  }  
}

