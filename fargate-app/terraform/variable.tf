provider "aws" {
  region = "ap-northeast-1"
}

terraform {
  backend "s3" {
    bucket = "terraform-state-es"
    key = "event-queue.tfstate"
    region = "ap-northeast-1"
    profile = "terraform"
  }
}

variable "category" {
  default = "example"
}
variable aws_profile {
  default = "terraform"
}
//variable "access_key" {}
//variable "secret_key" {}
variable "region" {
  default = "ap-northeast-1"
}

variable "task_name" {
  default = "farget-app"
}

variable "stage" {
  default = "staging"
}

