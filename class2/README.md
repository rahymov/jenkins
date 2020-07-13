# Jenkins Class 3 
## Topic Docker build
#devops/jenkins/class/3

Create a Jenkins job to build docker image to docker hub.  Please follow the bellow requirements.
1. Make sure user can select the version to build from params
2. The Jenkins job should build and push to docker Hub
3. Use secure way to login with docker hub do not hard code any creds to pipeline


## Solution 
We need to create the parameters for user so user can go ahead and select the version.  The parameter `gitParameter` responsible to get the exactly the version to be build and will save the selected the version as `release_name`
```
properties([
  parameters([
    gitParameter(branch: '', branchFilter: 'origin/(.*)', 
    defaultValue: 'origin/version/0.1', description: 'Please go ahead  and select the version ', 
    name: 'release_name', quickFilterEnabled: false, selectedValue: 'NONE', 
    sortMode: 'NONE', tagFilter: 'origin/(.*)', type: 'PT_BRANCH', useRepository: 'https://github.com/fuchicorp/artemis')
  ])
])
```

Scheduling the node to run the build
```
podTemplate(name: k8slabel, label: k8slabel, yaml: slavePodTemplate, showRawYaml: false) {
  node(k8slabel) {
   // all steps goes here
	}
}
```


## Pull SCM
Responsible to pull the source from GitHub in this case. 
NOTE: before we pull the code we are using `params.release_name` to get exactly the version to be pulled

```
stage('Pull SCM') {
  git branch: "${params.release_name}", url: 'https://github.com/fuchicorp/artemis'
}
```


## Docker Build
After we have pull the source code we will need to run the build. The `Dockerfile` is already exist so we will need to make sure docker command installed and run the  `docker build -t example .` 
NOTE: Here we are go into  to the `docker` container and  also using `release_name` to able to build right version 
```
stage("Docker Build") {
  container("docker") {
    sh "docker build -t fsadykov/artemis:${release_name.replace('version/', 'v')}  ."
  }
}
```



## Docker Login
We have created a credential call `docker-hub-creds` which is contains our `username` and `passworrd` so Jenkins can use that securely 
```
stage("Docker Login") {
  withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', passwordVariable: 'password', usernameVariable: 'username')]) {
    container("docker") {
      sh "docker login --username ${username} --password ${password}"
    }
  }
}
```



## Docker Push
Final stage will be the docker push which which is responsible to push the docker image to docker hub in this case. Same steps going to same container to be able to run docker commands and pushing to user `fsadykov` to specific repository `artemis` it will also include the version `release_name`
```
stage("Docker Push") {
  container("docker") {
    sh "docker push fsadykov/artemis:${release_name.replace('version/', 'v')}"
  }
}
```



