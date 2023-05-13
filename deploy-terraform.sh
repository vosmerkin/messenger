#!/bin/sh -x

terraform -chdir="./deploy" plan -out aws-app-stack-plan

terraform -chdir="./deploy" apply "aws-app-stack-plan"

read -p "Press enter to continue"

terraform -chdir="./deploy" destroy -auto-approve