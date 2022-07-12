pipeline {
    agent any
    triggers {
        GenericTrigger(
         genericVariables: [
          [key: 'ref', value: '$.ref']
         ],
         causeString: 'Triggered on $ref',
         token: 'abc123',
         printContributedVariables: true,
         printPostContent: true,
         silentResponse: false,
         regexpFilterText: '$ref',
         regexpFilterExpression: 'refs/heads/' + BRANCH_NAME
        )
    }
    tools {
        maven '3'
    }
    stages {
        stage('构建') {
            steps {
                sh 'mvn install'
            }
        }
        stage('部署') {
            steps {
                sh 'docker build -f Dockerfile.deploy -t ceea-backend:latest .'
                sh 'docker container rm -f ceea-backend'
                sh 'docker run -d --name ceea-backend --restart=on-failure:3 -v /home/ceea/backend:/data -p 9090:9090 ceea-backend'
            }
        }
        stage('清理') {
            steps {
                sh 'docker image prune -f'
                sh 'docker volume prune -f'
            }
        }
    }
}