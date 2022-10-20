variable "region" {
  description = "AWS region"
  type        = string
}

variable "ami_id" {
  type = string
#  default = "ami-08895422b5f3aa64a" Suse
  default = "ami-0022f774911c1d690"
}
variable "ami_key_pair_name" {}

variable "aws_access_key" {
  description = "AWS access key"
  type        = string
}

variable "aws_secret_key" {
  description = "AWS secret key"
  type        = string
}

variable "engine_version" {
  default     = "12.10"
  type        = string
  description = "Database engine version"
}

variable "tags" {
  default     = {Name = "Messenger"}
  type        = map(string)
  description = "Extra tags to attach to the RDS resources"
}

variable "instance_type" {
  type    = string
  #  default = "t2.micro"
  default = "t3.nano"
  description = "EC2 node type"
}

variable "db_instance_type" {
  default     = "db.t3.micro"
  type        = string
  description = "Instance type for database instance"
}

variable "database_identifier" {
  default     = "edumessenger"
  type        = string
  description = "Identifier for RDS instance"
}

variable "database_username" {
  type        = string
  description = "Name of user inside storage engine"
}

variable "database_password" {
  type        = string
  description = "Database password inside storage engine"
}