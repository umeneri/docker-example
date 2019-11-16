resource "aws_sqs_queue" "deadletter_queue" {
  name = "${var.task_name}-${var.stage}-deadletter-queue"
}

resource "aws_sqs_queue" "app_queue" {
  name = "${var.task_name}-${var.stage}-queue"
}