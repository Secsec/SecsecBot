pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        git(url: 'https://github.com/Secsec/SecsecBot', branch: 'master')
        sh 'mvn package'
        stash 'secsec-0.0.1-SNAPSHOT.jar'
      }
    }
    stage('Build Image') {
      steps {
        unstash 'secsec-0.0.1-SNAPSHOT.jar'
        sh 'oc start-build cart --from-file=target/secsec-0.0.1-SNAPSHOT.jar --follow'
      }
    }
  }
  tools {
    maven 'maven'
  }
  environment {
    maven = ''
  }
}
