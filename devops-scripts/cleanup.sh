#!/bin/bash

# Complete clean up all services & gateways

shopt -s extglob

apps=("gateway" 
"customer-service"
"supplier-service"
"product-service"
"invoice-service" )
for app in "${apps[@]}";
do
    ( cd ../$app && rm -r !(entities.jh|.yo-rc.json|../.gitignore) )
done

for app in "${apps[@]}";
do
    ( cd ../$app && rm -r .jhipster/ )
done

for app in "${apps[@]}";
do
    ( cd ../$app && rm -r .mvn/ && rm -r .idea/ )
done

for app in "${apps[@]}";
do
    ( cd ../$app && rm -r .idea/ )
done

for app in "${apps[@]}";
do
    ( cd ../$app && rm -r .DS_STORE && rm -r .editorconfig && rm -r .gitattributes )
done

cd ../root-module && rm -r !(pom.xml | .gitignore)
