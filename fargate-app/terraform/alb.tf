data "template_file" "lb_bucket_policy" {
  template = "${file("file/lb_bucket_policy.tpl")}"

  vars = {
    task_name = "${var.task_name}"
  }
}
resource "aws_s3_bucket" "lb_bucket" {
  bucket = "${var.task_name}-lb-log"
  acl    = "private"
  policy = "${data.template_file.lb_bucket_policy.rendered}"
}

resource "aws_lb" "app_lb" {
  name = "${var.task_name}-alb"
  internal = false
  load_balancer_type = "application"
  security_groups = [
    "${aws_security_group.app_sg.id}"]
  subnets = [
    "${aws_subnet.app_subnet_a.id}",
    "${aws_subnet.app_subnet_c.id}",
  ]
  enable_deletion_protection = false

  access_logs {
    bucket = "${aws_s3_bucket.lb_bucket.bucket}"
    prefix = "${var.task_name}"
    enabled = true
  }
}

resource "aws_lb_listener" "app_listener" {
  load_balancer_arn = "${aws_lb.app_lb.arn}"
  port              = "80"
  protocol          = "HTTP"
//  ssl_policy        = "ELBSecurityPolicy-2016-08"
//  certificate_arn   = "arn:aws:iam::187416307283:server-certificate/test_cert_rab3wuqwgja25ct3n4jdj2tzu4"

  default_action {
    type             = "forward"
    target_group_arn = "${aws_lb_target_group.app_target_group.arn}"
  }
}

resource "aws_lb_target_group" "app_target_group" {
  name = "${var.task_name}-lb-tg"
  port = 80
  protocol = "HTTP"
  vpc_id = "${aws_vpc.app_vpc.id}"
  target_type = "ip"

  health_check {
    interval            = 30
    path                = "/"
    port                = 80
    protocol            = "HTTP"
    timeout             = 5
    unhealthy_threshold = 2
    matcher             = 200
  }
}