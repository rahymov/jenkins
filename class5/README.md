# Jenkins class 5
## Topic Terraform 
#devops/jenkins/class

Create a Jenkins job to deploy instance. Jenkins job should deploy the source to below environments. Also see the below environment variables to deploy the terraform code

## Jenkins job should 
1. Jenkins job should pull the source code
2. Generate the `tfvars` 
3. Should apply/destroy/plan based on Jenkins params


### Environment Variables
```
export AWS_ACCESS_KEY='something'
export AWS_SECRET_KEY='something'
export AWS_DEFAULT_REGION='us-east-1'
```

### Environments
1. DEV
2. QA
3. PROD
4. STAGE

