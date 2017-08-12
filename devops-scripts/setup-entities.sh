#!/bin/bash

#Define the folders in which we will run `yo jhipster:import-jdl entities.jh`

apps=("gateway" 
"customer-service"
"supplier-service"
"product-service"
"invoice-service")
for app in "${apps[@]}";
do
    ( cd ../$app && yo jhipster:import-jdl entities.jh )
done