#!/bin/bash

# Generate entities in front end gateway

cd ../gateway

entities=("Customer" 
"CustomerCategory" 
)
for entity in "${entities[@]}";
do
    ( yes "" | yo jhipster:entity $entity && wait) 
done
