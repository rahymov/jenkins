# Jenkins class 2 
#devops/jenkins/class/2


#### What Jenkins jobs?

Simply put, you can think of a Jenkins build job as a particular task or step in your build process. Jenkins will run all task you put inside your Jenkins jobs. 
Jobs are any runnable task which is controller by Jenkins


## Jobs
1. Create 
2. Delete
3. Update 


## Job features
Pipeline Syntax
Recent Changes
Configure


## Hello world job
```
print('Hello World')
```



# Groovy Syntax and datatypes
Groovy create empty string `first_name` which will contain `Frank`
```
def first_name = "Frank" // String Type 
println(first_name)
```

List of names in groovy `names` will contain the list of names `Anna`,  `Nastya ` and `Vova `
```
def names = ["Anna", "Nastya", "Vova" ] // List of Strings
println(names)
```

Map type of the variables which will contain everything about one person 
```
def myInfo = [
    "first_name": "Farkhod", 
    "last_name": "Sadykov"
    ] // Map in groovy 
println(myInfo)
```

Boolean type of the veritable which is set to false in this case 
```
def debugMode = false // Bool variale in groovy 
println(debugMode)
```

function `sayHello` will will print `Hello World ` when it has been called
```
def sayHello() { // Functions in groovy 
    println('Hello World')
}
sayHello() // Call the function in groovy 
```


## Jenkins node schedule
Jenkins uses plugin to spin up one node and run the job on top of that. Node can be configured from the job and added all features. See `k8slabel` which is creating a uniq string which will store name of the node or label on Kubernetes. NOTE: Kubernetes uses uniq name for each manifest 
```
def k8slabel = "jenkins-pipeline-${UUID.randomUUID().toString()}"
```

After you have uniq name for your node/POD you will need yaml structure. See `slavePodTemplate` which will run a container `docker` which is can be used for docker builds  or any other docker related features.  
```
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
```

After you have name and yaml you will need to spin up the node on top of Kubernetes. In example below you have  See example bellow
```
podTemplate(name: k8slabel, label: k8slabel, yaml: slavePodTemplate, showRawYaml: false) {
	node(k8slabel) {
	// All actions goes here
	}
}
```


