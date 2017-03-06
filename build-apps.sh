#!/bin/bash

#Define the folders in which we will run `mvn package docker:build -DskipTests=true`


apps=("gateway" 
"customerService" 
"supplierService" 
"productService" 
"invoiceService")
for app in "${apps[@]}";
do
    ( cd $app && mvn clean package -Pprod docker:build -DskipTests ) 
done

wait;

echo "built all!"