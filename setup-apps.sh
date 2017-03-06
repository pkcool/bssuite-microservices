#!/bin/bash

# Define the folders in which we will run `yo jhipster`

apps=("gateway" 
"customerService" 
"supplierService" 
"productService" 
"invoiceService")
for app in "${apps[@]}";
do
    ( cd $app && yo jhipster --force --with-entities ) 
done
