#!/bin/bash

# Define the folders in which we will run `yo jhipster`

apps=("gateway" 
"customer-service"
"supplier-service"
"product-service"
"invoice-service")
for app in "${apps[@]}";
do
    ( cd ../$app && yo jhipster --force --with-entities )
done
