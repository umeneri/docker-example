resource "aws_security_group" "app_sg" {
  name = "${var.task_name}-${var.stage}-sg"
  vpc_id = "${aws_vpc.app_vpc.id}"

  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = [
      "0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = [
      "0.0.0.0/0"]
  }

  tags = {
    Name = "${var.task_name}-${var.stage}-sg"
  }
}
