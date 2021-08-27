# Spring Cloud Configuration Server
This service is a module of micro-service system that provide APIs to get startup configuration from all servers inside the system.
This module will be executed from CI workflow as below:
 - Docker build with Github Action
 - Store Docker image to AWS ECR
 - Deploy to AWS Fargate and manage with AWS ECS