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
scp -r  servico/target/service.war 	vander@agentesdabolsa.com.br:/home/vander/server
scp -r  visao/dist 				vander@agentesdabolsa.com.br:/home/vander/server

#ssh -i ~/.ssh/agdb.pem ubuntu@agentesdabolsa.com.br