//resource "aws_ecr_repository" "app_repository" {
//  name = "${var.task_name}-${var.stage}"
//}
//
resource "aws_ecr_repository" "nginx_repository" {
  name = "nginx"
}
