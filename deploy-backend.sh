#!/bin/bash
#

./gradlew -p backend clean build -x test dist -Dprofile=dev 
