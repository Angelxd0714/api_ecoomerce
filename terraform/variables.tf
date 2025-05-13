variable "aws_region" {
  default = "us-east-2"
}

variable "key_name" {
  description = "Nombre del par de llaves SSH"
  type        = string
}

variable "instance_type" {
  default = "t3.micro"
}

