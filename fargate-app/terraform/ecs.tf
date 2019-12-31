resource "aws_ecs_cluster" "app_ecs_cluster" {
  name = "${var.task_name}-${var.stage}-cluster"
}

variable "log_group" {
  default = "/ecs/fargat/task"
}

resource "aws_ecs_service" "app_ecs_service" {
  name = "${var.task_name}-${var.stage}-service"
  cluster = "${aws_ecs_cluster.app_ecs_cluster.id}"
  task_definition = "${var.task_name}-${var.stage}"
  desired_count = 1
  launch_type = "FARGATE"
  deployment_minimum_healthy_percent = 100
  deployment_maximum_percent = 200
  health_check_grace_period_seconds = 100

  network_configuration {
    subnets = [
      "${aws_subnet.app_subnet_a.id}",
      "${aws_subnet.app_subnet_c.id}"]
    security_groups = [
      "${aws_security_group.app_sg.id}"
    ]
//    assign_public_ip = true
    assign_public_ip = false
  }

  load_balancer {
    target_group_arn = "${aws_lb_target_group.app_target_group.arn}"
    container_name = "nginx"
    container_port = 80
  }
}

//resource "aws_ecs_task_definition" "app_task_definition" {
//  family = "${var.task_name}-${var.stage}-task-definition"
//  requires_compatibilities = [
//    "FARGATE"
//  ]
//  network_mode = "awsvpc"
//  cpu = "256"
//  memory = "512"
//  execution_role_arn = "${aws_iam_role.app_ecs_task_execution_role.arn}"
//  task_role_arn = "${aws_iam_role.app_task_role.arn}"
//
//  container_definitions = <<DEFINITION
//[
//  {
//    "name": "${var.task_name}-${var.stage}",
//    "image": "772010606571.dkr.ecr.ap-northeast-1.amazonaws.com/${var.task_name}-${var.stage}:latest",
//    "essential": true,
//    "portMappings": [],
//    "environment": [],
//    "logConfiguration": {
//      "logDriver": "awslogs",
//      "options": {
//        "awslogs-group": "${var.log_group}",
//        "awslogs-region": "${var.region}",
//        "awslogs-stream-prefix": "fargate"
//      }
//    }
// }
//]
//DEFINITION
//}

resource "aws_cloudwatch_log_group" "app_log" {
  name = "${var.log_group}"
  retention_in_days = 14
}
