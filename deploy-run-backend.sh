#!/bin/bash
set -x

echo EC2_IP to ui network-dev.properties
EC2_IP=54.196.76.203

echo RDS_ADDRESS to backend network-dev.properties
RDS_ADDRESS=edumessenger.cfufiisvplbv.us-east-1.rds.amazonaws.com

echo copy files to aws
scp -i /home/evgen/dev_win2.pem backend/dist/backend-dev.zip ec2-user@$EC2_IP:/opt/messenger_backend


ssh -i /home/evgen/dev_win2.pem ec2-user@$EC2_IP bash -c "'
echo $RDS_ADDRESS
echo $RDS_ADDRESS

echo unzip
unzip /opt/messenger_backend/backend-dev.zip -d /opt/messenger_backend/

echo create messenger db on rds from aws CLI
createdb --host=$RDS_ADDRESS --port=5432 --username=postgres --password messenger

echo Run backend from aws console
cd /opt/messenger_backend/
java -jar backend-0.1.1-SNAPSHOT.jar


'"

#echo unzip
#ssh  -i /home/evgen/dev_win2.pem ec2-user@$EC2_IP unzip /opt/messenger_backend/backend-dev.zip -d /opt/messenger_backend/
#
#echo create messenger db on rds from aws CLI
#ssh  -i /home/evgen/dev_win2.pem ec2-user@$EC2_IP createdb --host=$RDS_ADDRESS --port=5432 --username=postgres --password messenger
#
#echo Run backend from aws console
#ssh  -i /home/evgen/dev_win2.pem ec2-user@$EC2_IP java -jar -Dspring.profiles.active=dev /opt/messenger_backend/backend-0.1.1-SNAPSHOT.jar
