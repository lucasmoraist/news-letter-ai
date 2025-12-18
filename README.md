# News Letter AI 🚀

**News Letter AI** é uma aplicação micro-SaaS desenvolvida em Java e Spring Boot, projetada para compartilhar notícias personalizadas geradas por Inteligência Artificial. O sistema automatiza o processo de curadoria e envio de informativos baseados em temas específicos.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 21.
* **Framework:** Spring Boot 4.0.0.
* **Banco de Dados:** PostgreSQL (Produção) e H2 (Desenvolvimento/Testes).
* **IA:** Google Gemini SDK.
* **Comunicação:** Spring Cloud OpenFeign para integração com APIs externas (Brevo).
* **Containerização:** Docker e Docker Compose.
* **Outros:** Flyway (Migrações), Lombok, Caffeine Cache, JaCoCo (Relatórios de cobertura).

## 📋 Pré-requisitos

Antes de iniciar, você precisará ter instalado:

* [JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
* [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/)
* Uma chave de API do **Google Gemini**
* Uma conta e chave de API do **Brevo** (para envio de e-mails)

## ⚙️ Configuração Local

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/news-letter-ai.git
cd news-letter-ai

```

### 2. Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto baseado no arquivo `.env.example` e preencha com suas credenciais:

```properties
SECRETS_GOOGLE_GENAI_API_KEY=sua_chave_gemini_aqui
JSM_EMAIL=seu_email_remetente_brevo
SECRETS_BREVO_API_KEY=sua_chave_api_brevo

```

### 3. Subir o Banco de Dados

O projeto utiliza Docker Compose para gerenciar o banco de dados PostgreSQL e o pgAdmin. Execute:

```bash
docker-compose up -d

```

### 4. Executar a Aplicação

Você pode rodar a aplicação via Gradle:

```bash
./gradlew bootRun

```

A aplicação estará disponível em `http://localhost:8080`.

## 🚀 Endpoints Principais

Alguns endpoints estão configurados para ignorar a segurança padrão para facilitar a integração:

* **Salvar Cliente:** `POST /api/v1/customer/save`
* **Health Check:** `/actuator/health`

## 🧪 Testes e Cobertura

Para rodar os testes unitários e gerar o relatório de cobertura do JaCoCo:

```bash
./gradlew test

```

O relatório HTML estará disponível em: `build/reports/jacoco/test/html/index.html`.

---