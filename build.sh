#!/bin/sh

echo "**********************************************"
echo "  Build Agentes da Bolsa    "
echo "**********************************************"

echo "git pull"
git pull

cd servico

echo "mvn clean package"
mvn clean package -Dmaven.test.skip=true

cd ../visao
cp app/scripts/config.prod.js app/scripts/config.js 
grunt clean build

echo "copias locais"
cp app/scripts/config.local.js app/scripts/config.js 

cp app/ace/mode-java.js dist
cp app/ace/theme-eclipse.js dist

cd ..
echo "enviando"
scp -r  -i ~/.ssh/agdb.pem servico/target/agentesdabolsa.war 	ubuntu@agentesdabolsa.com.br:/home/ubuntu/devvander
scp -r  -i ~/.ssh/agdb.pem visao/dist 				ubuntu@agentesdabolsa.com.br:/home/ubuntu/devvander

ssh -i ~/.ssh/agdb.pem ubuntu@agentesdabolsa.com.br