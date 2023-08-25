#!/bin/bash

awslocal dynamodb create-table --table-name QuarkusFruits --attribute-definitions AttributeName=fruitName,AttributeType=S --key-schema AttributeName=fruitName,KeyType=HASH --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1

awslocal dynamodb put-item --table-name QuarkusFruits --item '{ "fruitName": {"S": "orange"}, "fruitDescription": {"S": "brightly colored citrus fruit"} }'

echo 'init_complete'