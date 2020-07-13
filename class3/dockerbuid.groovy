def k8slabel = "jenkins-pipeline-${UUID.randomUUID().toString()}"
def slavePodTemplate = """
      metadata:
        labels:
          k8s-label: ${k8slabel}
        annotations:
          jenkinsjoblabel: ${env.JOB_NAME}-${env.BUILD_NUMBER}
      spec:
        affinity:
          podAntiAffinity:
            requiredDuringSchedulingIgnoredDuringExecution:
            - labelSelector:
                matchExpressions:
                - key: component
                  operator: In
                  values:
                  - jenkins-jenkins-master
              topologyKey: "kubernetes.io/hostname"
        containers:
        - name: docker
          image: docker:latest
          imagePullPolicy: IfNotPresent
          command:
          - cat
          tty: true
          volumeMounts:
            - mountPath: /var/run/docker.sock
              name: docker-sock
        serviceAccountName: default
        securityContext:
          runAsUser: 0
          fsGroup: 0
        volumes:
          - name: docker-sock
            hostPath:
              path: /var/run/docker.sock
    """
    properties([      //The parameter gitParameter responsible to get the exactly the version to be build and will save the selected the version as release_name
        parameters([
            gitParameter(branch: '', branchFilter: 'origin/(.*)', 
            defaultValue: 'origin/version/0.1', description: 'Please go ahead  and select the version ', 
            name: 'release_name', quickFilterEnabled: false, selectedValue: 'NONE', 
            sortMode: 'NONE', tagFilter: 'origin/(.*)', type: 'PT_BRANCH', useRepository: 'https://github.com/fuchicorp/artemis')
            ])
            ])
    
    //Scheduling the node to run the build
    podTemplate(name: k8slabel, label: k8slabel, yaml: slavePodTemplate, showRawYaml: false) {
      node(k8slabel) {
        stage('Pull SCM') {     //Responsible to pull the source from GitHub in this case. NOTE: before we pull the code we are using params.release_name to get exactly the version to be pulled
            git branch: "${params.release_name}", url: 'https://github.com/fuchicorp/artemis'
        }
        
        //pull the source code we will need to run the build
        stage("Docker Build") {
            container("docker") {
                sh "docker build -t sevil2020/artemis:${release_name.replace('version/', 'v')}  ."
            }
        }

        //We have created a credential call docker-hub-creds which is contains our username and passworrd so Jenkins can use that securely
        stage("Docker Login") {
            withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', passwordVariable: 'password', usernameVariable: 'username')]) {
                container("docker") {
                    sh "docker login --username ${username} --password ${password}"
                }
            }
        }
        //We have created a credential call docker-hub-creds which is contains our username and passworrd so Jenkins can use that securely
        stage("Docker Push") {
            container("docker") {
                sh "docker push sevil2020/artemis:${release_name.replace('version/', 'v')}"
            }
        }
      }
    }
