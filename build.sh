#!/bin/sh

echo "**********************************************"
echo "  Build Agentes da Bolsa    "
echo "**********************************************"

echo "git pull"
git pull

cd servico

echo "mvn package"
mvn clean package

cd ../visao
cp app/scripts/config.prod.js app/scripts/config.js 
grunt clean build --force
cp app/scripts/config.local.js app/scripts/config.js 

cd ..
echo "enviando"
scp -r  -i ~/.ssh/agdb.pem servico/target/agentesdabolsa.war 	ubuntu@agentesdabolsa.com.br:/home/ubuntu/devvander
scp -r  -i ~/.ssh/agdb.pem visao/dist 				ubuntu@agentesdabolsa.com.br:/home/ubuntu/devvander