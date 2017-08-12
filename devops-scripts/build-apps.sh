#!/bin/bash

#Define the folders in which we will run `mvn package docker:build -DskipTests=true`


apps=("gateway" 
"customer-service"
"supplier-service"
"product-service"
"invoice-service")
for app in "${apps[@]}";
do
    ( cd ../$app && mvn clean package -Pprod docker:build -DskipTests )
done

wait;

echo "built all!"