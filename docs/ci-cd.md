Continuous Integration & Continous Devliery with Jenkins and Heroku cloud

1. Start Jenkins using docker image
2. Install Heroku CLI in the jenkins docker container
    By default, root is disabled for jenkins container, we could either create a custom docker image on top of the official jenkins image,
    or install Heroku CLI in the jenkins container.
    To install Heroku CLI, tty into the container:
    docker exec -u 0 -it mycontainer bash    (replace mycontainer with the container's name)
    
    & and then install:
     wget https://cli-assets.heroku.com/branches/stable/heroku-linux-386.tar.gz -O heroku.tar.gz
     mkdir -p /usr/local/lib
     tar -xvzf heroku.tar.gz -C /usr/local/lib
     /usr/local/lib/heroku/install
