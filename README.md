# BSSuite with microservices architecture

Business Solution Suite

## Summary
a cloud ERP system based on microservice architecture and docker.

It allows you to run:
- The registry
- An API Gateway
- Several microservices (customerService, supplierService, etc.) based on different databases.
- The ELK stack (Elasticsearch, Logstash, Kibana) for log centralization
- _(future) Graphite/Grafana for metrics centralization_

#### **All working out of the box !**

It provides:
- Scripts to setup the apps
- `.yo-rc.json` files in customerService, supplierService, invoiceService, productService directory that will be used to generate apps
- A `central-server-config/` directory that can be used to edit the registry's config server configuration but _only in dev profile_ (a git repository is used in prod profile)

It depends on [generator-jhipster-docker-compose](https://github.com/jhipster/generator-jhipster-docker-compose) to generate a global docker-compose file.

## How to test

### Setup and build
First, generate all apps from their _.yo-rc.json_.

    ./setup-apps.sh
    
Then, generate samples entities from the _entities.jh_ JDL file in each app's directory

    ./setup-entities.sh
    
Then, generate entities for front end gateway. This step needs to be done manually.
    "cd gateway" & generate entities one by one (check the command in the below script).

    ./setup-gateway-entities.sh

Finally, build apps and generate docker images for them.  `mvn package docker:build -DskipTests=true`

    ./build-apps.sh
    
This script runs `mvn package docker:build -DskipTests=true` for all apps, the `app/src/main/docker/Dockerfile` is used by maven-docker plugin to build the docker image.

Generate a Docker-compose file:

    mkdir docker-compose
    cd docker-compose

    yo jhipster:docker-compose

And answer the questions.

To run all apps:
    
    docker-compose up
   
To shut down all apps:

    docker-compose down


### Run everything

Note: At any point in the process you can use `docker-compose logs appname` to view its logs.

#### Start the JHipster Eegistry (service discovery and configuration server)

- `docker-compose up -d jhipster-registry`: launch the registry
- Open `http://localhost:8761/` to view the Eureka console (new microservices instances will automatically register themselves and show up here)
- Open `http://localhost:8761/config/application-dev.yml` to have a look at the properties that are transfered to all apps in the dev profile. You can edit them in the `/central-server-config` directory.

#### ELK (log centralization)

- `docker-compose up -d elk-elasticsearch elk-logstash elk-kibana`
- Open Kibana: `http://localhost:5601`, all logs will show up here.

#### Gateway and microservices

Start the Gateway with:
- `docker-compose up -d gateway`

It should connect with the registry and show up in the Eureka console.
- Open the gateway's admin panel: `http://localhost:8080/#/gateway` (log in with admin/admin)

Also logs should have started to show up in Kibana.

Start customerService with:
- `docker-compose up -d customerService`

Start the other apps:
- `docker-compose up -d supplierService invoiceService`

#### Scale your apps

You can scale an app by creating **multiple instances** of it (doesn't work on the gateway or other apps that have their ports binded to localhost):
- `docker-compose scale customerService=2`
- `docker-compose scale supplierService=3`

Then wait for them to show up at `http://localhost:8761/` and `http://localhost:8080/#/gateway`.

#### Stop an app
- `docker-compose stop appname`


## Shutdown and clean up
- Simply run `docker-compose down`
The following commands may prove useful:
- `docker stop $(docker ps -a -q)`: Stop all running containers
- Then `docker rm $(docker ps -a -q)`: Remove all containers

## Clean everything
Run cleanup script

    ./cleanup.sh

## Deploy to Heroku
- Download Heroku CLI & set up account
- Deploy registry: https://dashboard.heroku.com/new?&template=https%3A%2F%2Fgithub.com%2Fjhipster%2Fjhipster-registry
    (registry url is to be used when deploying gateways and services in next steps)
- Deploy gateway and services one by one: 

    `cd gateway`
    
    `yo jhipster:heroku` (enter registry url in the form of https://[appname].herokuapp.com)
    
    `heroku config:set JHIPSTER_REGISTRY_URL="https://admin:[password]@[appname].herokuapp.com"` (replace with registry password & registry app name
    
   Do the same for all the services, i.e.
   
   `cd customerService`
       
   `yo jhipster:heroku`
   
   `heroku config:set JHIPSTER_REGISTRY_URL="https://admin:[password]@[appname].herokuapp.com"`
    
   -----
   for heroku clould, if the application is not started within 90s, db might be lock, to unlock, run:
   
   `heroku pg:psql -c "update databasechangeloglock set l
   ocked=false;" --app bss-customer-svc`
   
   or re-do:
   
   `heroku config:set JHIPSTER_REGISTRY_URL="https://admin:[password]@[appname].herokuapp.com"`
   
   if doesn't work, redeploy the application manually from command line:
   
   `yo jhipster:heroku`
   
   
## Troubleshooting
- git error msg in service/gateway projects
.git folder will be created when running yo jhipster:heroku or other generator inside services/gateways,
need to remove .git folder under services/gateways.

## TODO
- Switch between dev and prod ~~with an environment variable~~ different compose files.
- Boot up the database by extending src/main/docker/prod.yml
- (Bonus) Use log_driver to forward database logs to ELK through syslog
