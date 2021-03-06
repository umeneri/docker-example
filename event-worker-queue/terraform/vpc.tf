resource "aws_vpc" "app_vpc" {
  cidr_block = "10.0.0.0/16"

  tags =  {
    Name = "${var.category}-vpc"
  }
}

resource "aws_subnet" "app_subnet_a" {
  vpc_id = "${aws_vpc.app_vpc.id}"
  cidr_block = "10.0.1.0/24"
  availability_zone = "${var.region}a"

  tags =  {
    Name = "${var.task_name}-subnet-a"
  }
}

resource "aws_subnet" "app_subnet_c" {
  vpc_id = "${aws_vpc.app_vpc.id}"
  cidr_block = "10.0.2.0/24"
  availability_zone = "${var.region}c"

  tags =  {
    Name = "${var.task_name}-subnet-c"
  }
}

resource "aws_internet_gateway" "app_internet_gateway" {
  vpc_id = "${aws_vpc.app_vpc.id}"

  tags =  {
    Name = "${var.category}-igw"
  }
}

resource "aws_route_table" "app_route_table" {
  vpc_id = "${aws_vpc.app_vpc.id}"

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "${aws_internet_gateway.app_internet_gateway.id}"
  }

  tags =  {
    Name = "${var.category}-rt"
  }
}

resource "aws_route_table_association" "app_table_association_a" {
  subnet_id = "${aws_subnet.app_subnet_a.id}"
  route_table_id = "${aws_route_table.app_route_table.id}"
}

resource "aws_route_table_association" "app_table_association_c" {
  subnet_id = "${aws_subnet.app_subnet_c.id}"
  route_table_id = "${aws_route_table.app_route_table.id}"
}
