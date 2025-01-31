# spring-ai
Accelerator to kick-start development of applications using Spring AI, Ollama and DeepSeek/Mistral. We develop a simple RAG (Retrieval-Augmented Generation) system using DeepSeek/Mistral and PostgreSQL Vector Database to chat with PDFs and answer complex questions about our local documents.

We use Spring Boot Data JDBC to connect to the Postgre Vector Database which is launched locally with [Docker Compose](https://docs.docker.com/compose/). Docker Compose requires the Docker engine to be already installed (our codespace has it).

[![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=923390219&machine=standardLinux32gb&location=SouthEastAsia)
---

### Prerequisites
The Codespaces for this repo pre-installs [Ollama](https://ollama.com/). However, after you have launched CodeSpaces, pull the Deepseek model deepseek-r1:1.5b which is 1.1 GB in size using the command below.

```bash
ollama pull deepseek-r1:1.5b
```
---

For the RAG (Retrieval-Augmented Generation) implementation, pull the latest Mistral model which is 4.1 GB in size using the command below.
```bash
ollama pull mistral:latest
```
You should be able to list the models and the Docker image for PostgreSQL 16.4 as shown below:
```
@kulkeez ➜ /workspaces/spring-ai (master) $ ollama list
NAME                ID              SIZE      MODIFIED    
mistral:latest      f974a74358d6    4.1 GB    2 hours ago    
deepseek-r1:1.5b    a42b25d8c10a    1.1 GB    7 hours ago    
@kulkeez ➜ /workspaces/spring-ai (master) $ docker images
REPOSITORY          TAG       IMAGE ID       CREATED        SIZE
pgvector/pgvector   pg16      c0fb89364d78   3 months ago   435MB
@kulkeez ➜ /workspaces/spring-ai (master) $ 
```

---
### Want to run it?
This Spring AI application can be launched from command-line by the developer using the command: ```mvn spring-boot:run ```
On launching the application the first time, the Postgre Vector database Docker image is pulled and started.
---

### Query this AI application
Launch any web browser and invoke this application with a prompt text. Example: http://localhost:8080/twister?message=Tell me a tongue twister


## Further Learning

### Books

- ["Spring AI in Action" by Craig Walls (Manning)](https://www.manning.com/books/spring-ai-in-action)

### Spring AI Documentation

- [Spring AI Project](https://spring.io/projects/spring-ai)
- [Spring AI Reference Documentation](https://docs.spring.io/spring-ai/reference/)
- [Spring AI API Documentation](https://docs.spring.io/spring-ai/docs/1.0.0-SNAPSHOT/api/)
- [Spring AI Playground](https://github.com/JM-Lab/spring-ai-playground) - A web UI designed to make it easy for Java developers to experiment with and integrate AI models.
