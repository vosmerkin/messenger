output "backend_ec2_ip" {
  value = ["${aws_instance.backend.*.public_ip}"]
  description = "EC2 public ip address"
}

output "db_hostname" {
  value       = aws_db_instance.postgresql.address
  description = "Public DNS name of database instance"
}

output "db_port" {
  value       = aws_db_instance.postgresql.port
  description = "Port of database instance"
}
