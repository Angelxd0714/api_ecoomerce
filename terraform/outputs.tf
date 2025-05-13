output "ecr_repository_url" {
  description = "The URL of the ECR repository"
  value       = aws_ecr_repository.localstack_ecr.repository_url
}

/*output "s3_bucket_name" {
  description = "The name of the S3 bucket"
  value       = aws_s3_bucket.localstack_bucket.bucket
}*/
