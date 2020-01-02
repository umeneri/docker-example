# push image to ECR
$ terraform apply --target=aws_ecr_repository.frontend
$ $(aws ecr get-login --no-include-email --region ap-northeast-1)

example:
$ docker build -t [image] .
$ docker tag [image]:latest [domain]/[image]:latest
$ docker push [domain]/[image]:latest

$ docker build -t frontend frontend
$ docker tag frontend:latest 772010606571.dkr.ecr.ap-northeast-1.amazonaws.com/staging-frontend:latest
$ docker push 772010606571.dkr.ecr.ap-northeast-1.amazonaws.com/staging-frontend:latest

$ docker build -t backend backend
$ docker tag backend:latest 772010606571.dkr.ecr.ap-northeast-1.amazonaws.com/staging-backend:latest
$ docker push 772010606571.dkr.ecr.ap-northeast-1.amazonaws.com/staging-backend:latest

# register task definition
```bash
$ aws ecs register-task-definition --cli-input-json file://ecs-task-def.json
```

# terraform apply
$ terraform init
$ terraform apply 

# operation verification
1. copy alb dns name
2. access dns by browser

# deploy by espresso


