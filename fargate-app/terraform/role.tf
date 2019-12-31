data "aws_caller_identity" "current" {}

resource "aws_iam_role" "app_task_role" {
  name               = "${var.task_name}-${var.stage}"
  assume_role_policy = "${data.aws_iam_policy_document.app_task_role_assume_role_policy_document.json}"
}

# assigns the app policy
resource "aws_iam_role_policy" "app_task_policy" {
  name   = "${var.task_name}-${var.stage}"
  role   = "${aws_iam_role.app_task_role.id}"
  policy = "${data.aws_iam_policy_document.app_task_policy_document.json}"
}

data "aws_iam_policy_document" "app_task_policy_document" {
  statement {
    actions = [
      "ecs:DescribeClusters",
    ]

    resources = [
      "${aws_ecs_cluster.app_ecs_cluster.arn}",
    ]
  }
}

# allow role to be assumed by ecs and local saml users (for development)
data "aws_iam_policy_document" "app_task_role_assume_role_policy_document" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}


resource "aws_iam_role" "app_ecs_task_execution_role" {
  name = "ecs-task-execution"
  assume_role_policy = "${data.aws_iam_policy_document.app_ecs_task_execution_role_policy_document.json}"
}

data "aws_iam_policy_document" "app_ecs_task_execution_role_policy_document" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

resource "aws_iam_role_policy_attachment" "app_ecs_task_execution_role_policy_attachment" {
  role = "${aws_iam_role.app_ecs_task_execution_role.name}"
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

