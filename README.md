# Microsserviço de autenticação com Spring + JWT + Zpikin + Kafka

<p>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white"/>
  <img src="https://img.shields.io/badge/Kafka-0f0f0f?style=for-the-badge&logo=apachekafka&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
</p>

---

## Projeto utilizando JWT para autenticação, Kafka para logs e Zipkin para observabilidade. Completamente desacoplado, e podendo servir até mesmo para outros projetos como um autenticador RBAC!

## Documentação

[Instalação](#instalação)  
[Documentação](#documentação)  
[Engenharia](#engenharia-do-sistema)

## Instalação:

Clone o projeto e rode-o com o seguinte comando na pasta raiz:

```
docker-compose up -d
```

A api é exposta em localhost:808 e o painel do zipkin é exposto em localhost:9411/zipkin

---

## Documentação:

Há 3 endpoints sendo expostos na porta 8080:

1. /auth/login
2. /auth/register
3. /auth/new-password

Sendo login e register utilizando do método POST, do protocolo HTTP, e new-password utilizando do método PATCH.

---

Register:

<p style="display:flex; align-items:center;gap:10px;"><img src="https://img.shields.io/badge/POST-yellow?style=for-the-badge"> Register:</p>

```
{
    "username": "string",
    "email": "string",
    "password": "string",
    "role": "ADMIN" ou "USER"
}
```

Retornando um 201 com a mensagem: _username_ registered successfully.

---

<p style="display:flex; align-items:center;gap:10px;"><img src="https://img.shields.io/badge/POST-yellow?style=for-the-badge"> Login:</p>

```
{
    "username": "string",
    "password": "string"
}
```

Retornando um 200 com o jwt como resposta.

---

<p style="display:flex; align-items:center;gap:10px;"><img src="https://img.shields.io/badge/PATCH-purple?style=for-the-badge
"> Change password:</p>

```
{
    "email": "string",
    "newPassword": "string"
}
```

Retornando um 200 com a mensagem: password successfully changed.

---

Após ter sucesso em login e register, é mandando para o kafka tópico auth-logs um registro da ação do usuário, com dados e timestamp.

---

## Engenharia do sistema


O sistema possui MAVEN como gerenciador de dependências, versão 17 do java e 3.5.3 do Spring Boot

Por ser um sistema pensado em microservice, ao se registrar ou logar, o serviço de autenticação envia um log no tópico 'user-auth' para o kafka, onde outros microsserviços poderiam estar escutando. Podendo, inclusive, formar um sistema EDA (Event Domain Architeture)

Utlizando Zipkin para métricas e Kafka para logs, o sistema possui uma grande observabilidade, tendo a possibilidade de ser examinado com mais precisão e eficiência e, consequentemente, possuindo uma performance ascendente dado à exposição de informações relevantes.
