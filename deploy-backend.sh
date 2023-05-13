#!/bin/bash

#export http_proxy=http://127.0.0.1:3128
#export https_proxy=http://127.0.0.1:3128

./gradlew -p backend clean build -x test dist -Dprofile=dev
#terraform -chdir="./deploy" apply -auto-approve
