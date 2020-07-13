# Jenkins Class 4  
## Topic Packer
#devops/jenkins/class/4 

Create a Jenkins job to run the packer build. Jenkins job should 

1. Pull the source code
2. Set all environment variables
3. Get or use existing packer command to build AMI on AWS
4. Make sure the job is using listed regions to build the AMI

#### Environment Variables
AWS access key and secret key to be able to access to AWS
```
export AWS_ACCESS_KEY='something'
export AWS_SECRET_KEY='something'
export AWS_REGION='us-east-1'
```

#### Regions
1. us-west-2
2. us-west-1
3. us-east-2
4. us-east-1
5. eu-west-1
