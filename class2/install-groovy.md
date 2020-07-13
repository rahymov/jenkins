# Install Groovy on Mac or Windows
#devops/jenkins/class

## Install groovy 
Here below you will see two options to install groovy first one is for mac second one is the link to install groovy. Please follow the steps and make sure you have groovy installed 


### Mac Users
#### Solution 1 
Get sdkman to install groovy 
```
curl -s get.sdkman.io | bash
```

Configure sdkman for your terminal put following data inside `~/.bashrc` or `~/.zsrhc` 
```
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

Run the following command to install groovy 
```
sdk install groovy
```

Check the installed or not 
```
groovy --version                                                                                                                            
Groovy Version: 3.0.4 JVM: 1.8.0_191 Vendor: Oracle Corporation OS: Mac OS X
```

#### Solution 2 
Install `brew` to get groovy 
```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
```

after you have `brew` you can run following command to install 
```
brew install groovy
```

Check the installed or not 
```
groovy --version                                                                                                                            
Groovy Version: 3.0.4 JVM: 1.8.0_191 Vendor: Oracle Corporation OS: Mac OS X
```


### Windows user
for Windows users you will need to follow the link
[The Apache Groovy programming language - Download](http://groovy-lang.org/download.html)
