provider "aws" {
  region                      = "us-east-1"
  access_key                  = "test"
  secret_key                  = "test"
  skip_credentials_validation = true
  skip_requesting_account_id  = true

  endpoints {
    ec2            = "http://localhost:4566"
    ecr            = "http://localhost:4566"
    s3             = "http://localhost:4566"
    iam            = "http://localhost:4566"
    sts            = "http://localhost:4566"
    cloudwatch     = "http://localhost:4566"
    logs           = "http://localhost:4566"
    secretsmanager = "http://localhost:4566"
  }
}

# Repositorio ECR en LocalStack
resource "aws_ecr_repository" "localstack_ecr" {
  name = "my-local-ecr"
}

# Crear un Bucket S3 (Simulado en LocalStack)
/*resource "aws_s3_bucket" "localstack_bucket" {
  bucket = "my-local-s3-bucket"
}*/
