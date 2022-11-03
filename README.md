# Simple chat messenger

This project is created for training Java knowledge. It will consist of two modules - backend an UI.




## Tech Stack

**Client:** Java, Swing, HttpClient, DTO

**Server:** Java, Spring Boot, Spring Data JPA, Flyway Migration, Postgresql


## Features

- Chat rooms
- Contact list
- Message history

## Installation

Create an Application User and group

```bash
  sudo groupadd -r messenger_group
  sudo useradd -r -s /bin/false -g messenger_group messenger_user
```
Create Systemd Service
First, create a systemd service file to manage our application

```bash
sudo vim /etc/systemd/system/backend.service
```

It will have content like below:

```bash
[Unit]
Description=Simple messenger backend service

[Service]
WorkingDirectory=/opt/messenger
ExecStart=/bin/java -Xms128m -Xmx256m -jar messenger_backend.jar
User=messenger_user
Type=simple
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Set User to the one created earlier, and WorkingDirectory to the directory with a jar file.

Give the user and group ownership permissions for the Project Directory:

```bash
sudo chown -R messenger_user:messenger_group /opt/messenger
```
reload systemd so that it knows of the new application added.
```bash
sudo systemctl daemon-reload
```


## Run Locally

Start Java Application service with systemd

```bash
  sudo systemctl start backend.service
```

To check the status, use:

```bash
  systemctl status backend
```

You can also enable the service to start on server boot:

```bash
  sudo systemctl enable backend
```

To restart the application, use:

```bash
  sudo systemctl restart backend
```



## 🚀 About Me
I'm a Java developer...
https://github.com/vosmerkin


## Roadmap
- Create UI
- Create backend basic application
- Add user login / logoff functionality
- Add chat rooms
- Test deploy
- Add messages functionality
- Add message history
