#!/bin/sh

# Conexao
# ssh -i ~/.ssh/agdb.pem ubuntu@agentesdabolsa.com.br

echo "**********************************************"
echo "  Build Agentes da Bolsa    "
echo "**********************************************"

export SERVER=ec2-54-233-128-73.sa-east-1.compute.amazonaws.com

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
scp -i ~/.ssh/agdb.pem -r servico/target/service.war ubuntu@$SERVER:/home/ubuntu/server
scp -i ~/.ssh/agdb.pem -r visao/dist ubuntu@$SERVER:/home/ubuntu/server
ssh -i ~/.ssh/agdb.pem ubuntu@$SERVER
