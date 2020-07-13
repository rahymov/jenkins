# Jenkins class 1 
## Topic Installation
#devops/course/jenkins #evolvecyber/jenkins #devops/jenkins/class/1
## Install Jenkins on Kubernetes

Install Jenkins 
```
helm install --name jenkins  --set master.serviceType=LoadBalancer stable/jenkins --version 2.1.0
```


You can get admin's password by following command
```
printf $(kubectl get secret --namespace default jenkins -o jsonpath="{.data.jenkins-admin-password}" | base64 --decode);echo
```


Wait for loadBalancer to pick up public IP 
```
kubectl get svc --namespace default -w jenkins
```


After that you can go ahead and get url of the Jenkins
```
export SERVICE_IP=$(kubectl get svc --namespace default jenkins --template "{{ range (index .status.loadBalancer.ingress 0) }}{{ . }}{{ end }}")
echo http://$SERVICE_IP:8080/login
```


### Jenkins configuration
1. System Message
2. Jenkins Location
3. Global Pipeline Libraries
4. E-mail Notification 


#### Plugin management
1. Installation 
2. Upgrade  
3. Remove  


#### Jenkins Credentials
1. Creation  
2. Delete
3. Update


#### Jenkins logs
All changes detected and you should be able to see all logs


#### About page
Jenkins Version 
Status licenses
Plugins for the Jenkins


#### Jenkins Console Script
1. Only administrator has access 
2. Use following script  `println('Hello World!')`


Get all display name 
```
Jenkins.instance.pluginManager.plugins.each() {
  println(it.getDisplayName())
}
```


IsEnabled function 
```
Jenkins.instance.pluginManager.plugins.each() {
  println(it.getDisplayName())
  if (it.isEnabled()) {
    println('Enabled yes')
  } else {
    println('Not enabled')
  }
}
```



## Questions
What is a Jenkinsfile? 
Why do we use Jenkins? 
What is meant by continuous integration in Jenkins? 
What is the difference between Hudson and Jenkins? 
How do you create a Job in Jenkins? 

