#!/usr/bin/env bash

docker build -t $1 $1
docker tag $1:latest 772010606571.dkr.ecr.ap-northeast-1.amazonaws.com/staging-$1:latest
docker push 772010606571.dkr.ecr.ap-northeast-1.amazonaws.com/staging-$1:latest
