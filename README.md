# spring-chatgpt
Accelerator to kick-start development of applications using Spring AI and OpenAI API (GPT-3)
OpenAI API offers models such as GPT, DALL-E and Whisper.

[![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=668986834&machine=standardLinux32gb&location=SouthEastAsia)
---
### OpenAI API Key
To run this application, you will need to obtain OpenAI API keys and configure it within the application.properties file. 
```
openai.api.key=<add-your-openai-api-key>
```
---
### Want to run it?
This Spring Boot application can also be launched from command-line by the developer using the command: ```mvn spring-boot:run ```
---
### Query the application
Launch any web browser and invoke URL http://localhost:8080/chat?prompt=What%20is%20huggingface with a prompt text as depicted in the figure.
![Invoke Chat Application with Prompt](https://github.com/kulkeez/spring-chatgpt/blob/main/images/spring-openapi-query.png "")
