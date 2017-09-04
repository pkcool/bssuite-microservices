Continuous Integration & Continous Devliery with Jenkins and Heroku cloud

# Install Jenkins locally
Go to the official site https://jenkins.io/2.0/

Download the jenkins.war

started it with the following command.
java -jar jenkins.war --httpPort=9000

Browse to http://localhost:9000/ to install Jenkins

Create a pipeline for each service and gateway

# Jenkins cloud
Create a Jenkins instance in the cloud, 
i.e. aws / azure / google compute engine
An option would be to use Bitnami Jenkins to deploy the jenkins to the instance.

Create a pipeline for each service and gateway.
i.e. one pipeline for customer service,
one pipeline for supplier service.
in each pipeline, only build and test the corresponding service.
to do so, in Jenkins file, use dir () block get into the folder.
i.e. for customer service, to do 'clean'

    stage('clean') {
        dir('customer-service') {
            sh "pwd"
            sh "ls -a"
            sh "chmod +x mvnw"
            sh "./mvnw clean"
        }

    }


#Setup Jenkins on Google compute engine
From Google Cloud Platform, Use Google Deployment Management to install Jenkins image (Created by Google, not Bitnami)
Jenkins should be started automatically.
SSH into the server and install Git (apt-get install git) and JDK (https://www.mkyong.com/java/how-to-install-oracle-jdk-8-on-debian/)

Setting the JAVA_HOME Environment Variable
sudo nano /etc/environment
In this file, add the following line, making sure to replace the highlighted path with your own copied path.

/etc/environment
JAVA_HOME="/usr/lib/jvm/java-8-oracle"
Save and exit the file, and reload it.

source /etc/environment
You can now test whether the environment variable has been set by executing the following command:

echo $JAVA_HOME
This will return the path you just set.

## Setup Heroku for CD
install Heroku CLI following the instructions on the office web.
and login

    heroku login

After login, get api key

    heroku auth:token
    
copy the api key, and set the environment variable HEROKU_API_KEY

    sudo vim /etc/environment
    
Add a line to the environment file

    HEROKU_API_KEY="{apikey}"
replace the {apikey} with the real api key.

    source /etc/environment
   
then reboot the machine or reboot jenkins.

audo deployment should work for heroku after these settings.


# Deploy to Heoku from Jenkins pipeline

        stage('package and deploy') {
            dir('customer-service') {
                sh "./mvnw com.heroku.sdk:heroku-maven-plugin:1.1.1:deploy -DskipTests -Pprod -Dheroku.appName=bss-customer-svc"
            }
        }

"appName" is the app name on Heroku. if no app on heroku with name, create an empty app with the name on Heroku first.



# Set up Jenkins with docker and heroku tool installation
1. Start Jenkins using docker image
2. Install Heroku CLI in the jenkins docker container
    By default, root is disabled for jenkins container, we could either create a custom docker image on top of the official jenkins image,
    or install Heroku CLI in the jenkins container.
    To install Heroku CLI, tty into the container:
    docker exec -u 0 -it jenkins bash    (replace jenkins with the container's name)
    
    & and then install:
     wget https://cli-assets.heroku.com/branches/stable/heroku-linux-386.tar.gz -O heroku.tar.gz
     mkdir -p /usr/local/lib
     tar -xvzf heroku.tar.gz -C /usr/local/lib
     /usr/local/lib/heroku/install

    Then: 
        heroku
        Enter your Heroku credentials.
        Email: e*m*@example.com
        Password (typing will be hidden):
        Authentication successful.
        
        
        
# Code analysis with Sonar locally
1. start Sonar server locally using docker compose (going to service/gateway directory)
    
        cd customer-service
        docker-compose -f src/main/docker/sonar.yml up
    
2. Analyze the code
        
        ./mvnw sonar:sonar
        
3. The Sonar reports will be available at: http://localhost:9000


