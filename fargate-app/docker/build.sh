#!/usr/bin/env bash

function usage() {
    cat <<EOF
build docker image and upload to ecr.

Usage:
    $(basename ${0}) [frontend | backend]
EOF

return 0
}

if [[ $# -ne 1 ]] ; then
    usage
    exit 1
else
    target=$1
fi

$(aws ecr get-login --no-include-email --region ap-northeast-1)
docker build -t $target $target
docker tag $target:latest 772010606571.dkr.ecr.ap-northeast-1.amazonaws.com/staging-$target:latest
docker push 772010606571.dkr.ecr.ap-northeast-1.amazonaws.com/staging-$target:latest
