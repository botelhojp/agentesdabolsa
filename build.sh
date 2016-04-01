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

grunt clean build