#!/bin/bash

yum install postgresql -y
curl https://truststore.pki.rds.amazonaws.com/us-east-1/us-east-1-bundle.pem > /etc/ssl/certs/us-east-1-bundle.pem

echo '[BellSoft]
name=BellSoft Repository
baseurl=https://yum.bell-sw.com
enabled=1
gpgcheck=1
gpgkey=https://download.bell-sw.com/pki/GPG-KEY-bellsoft
priority=1' > /etc/yum.repos.d/bellsoft.repo
yum install bellsoft-java17 -y

mkdir /opt/messenger_backend
chown ec2-user /opt/messenger_backend
