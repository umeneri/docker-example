provider "aws" {
  region = "ap-northeast-1"
}

terraform {
  backend "s3" {
    bucket = "terraform-fargate-app"
    key = "fargate-app.tfstate"
    region = "ap-northeast-1"
    profile = "terraform"
  }
}

variable aws_profile {
  default = "terraform"
}

variable "region" {
  default = "ap-northeast-1"
}

variable "category" {
  default = "example"
}

variable "task_name" {
  default = "fargate"
}

variable "env" {
  default = "staging"
}
