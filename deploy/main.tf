provider "aws" {
  region     = var.region
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}

resource "aws_security_group" "allow_ssh_web" {
  name = "ssh_web"
  ingress {
    from_port   = 22
    protocol    = "tcp"
    to_port     = 22
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 80
    protocol    = "tcp"
    to_port     = 80
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
  tags = {
    Name = "allow_ssh_web"
  }
}

resource "aws_iam_role" "ec2_role" {
  name = "my_role"

  assume_role_policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Action    = "sts:AssumeRole"
        Effect    = "Allow"
        Sid       = ""
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      },
    ]
  })
}


resource "aws_iam_role_policy_attachment" "cloud_watch_policy" {
  role       = aws_iam_role.ec2_role.name
  policy_arn = "arn:aws:iam::aws:policy/CloudWatchAgentServerPolicy"
}

resource "aws_iam_instance_profile" "some_profile" {
  name = "some_profile"
  role = aws_iam_role.ec2_role.name
}


resource "aws_instance" "backend" {
  ami                    = "${var.ami_id}"
  instance_type          = "t2.micro"
  key_name               = "${var.ami_key_pair_name}"
  vpc_security_group_ids = [aws_security_group.allow_ssh_web.id]

  iam_instance_profile = aws_iam_instance_profile.some_profile.id
  user_data            = file("ec2_startup.sh")
  tags                 = var.tags
}

