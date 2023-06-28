# A Demo Store Management in microservice system

This service is a module of microservice system that provide APIs to manage business information (
Ex: Sales, Products, Statistics, ...)
This module will be executed from CI workflow as below:

- Docker build with Github Action
- Store Docker image to AWS ECR
- Deploy to AWS Fargate and manage with AWS ECS
- Cover by VPC and AWS API Gateway
- Communicate with each other by AWS App Mesh
