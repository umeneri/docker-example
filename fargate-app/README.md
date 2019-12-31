# push image to ECR
$ terraform apply --target=aws_ecr_repository.nginx
$ $(aws ecr get-login --no-include-email --region ap-northeast-1)

example:
$ docker build -t [image] .
$ docker tag [image]:latest [domain]/[image]:latest
$ docker push [domain]/[image]:latest

$ docker build -t nginx nginx
$ docker tag nginx:latest 772010606571.dkr.ecr.ap-northeast-1.amazonaws.com/nginx:latest
$ docker push 772010606571.dkr.ecr.ap-northeast-1.amazonaws.com/nginx:latest


# register task definition
```bash
$ aws ecs register-task-definition --cli-input-json file://ecs-task-def.json
```

# terraform apply
$ terraform init
$ terraform apply 

# deploy by espresso



