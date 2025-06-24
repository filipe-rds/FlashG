# FlashG 📸

Uma plataforma social para fotógrafos compartilharem seus trabalhos, conectarem-se com outros profissionais e construírem uma comunidade vibrante de entusiastas da fotografia.

## 🎯 Sobre o Projeto

FlashG é uma rede social desenvolvida em Spring Boot, focada em fotógrafos e amantes da arte visual. A plataforma permite que usuários compartilhem suas fotografias, interajam através de curtidas e comentários, sigam outros fotógrafos e descubram novos talentos.

## ✅ Funcionalidades

### 🔐 Autenticação e Autorização

- Sistema de login/registro seguro
- Controle de acesso baseado em roles (USER/ADMIN)
- Autenticação com Spring Security

### 👤 Gestão de Perfil

- Criação e edição de perfil completo
- Upload de foto de perfil
- Controle de privacidade (aceitar/rejeitar seguidores)
- Sistema de bloqueio de comentários

### 📷 Compartilhamento de Fotos

- Upload de imagens com título e descrição
- Sistema de tags para categorização
- Visualização em galeria responsiva
- Suporte a diferentes formatos de imagem

### 🤝 Interações Sociais

- Sistema de seguir/deixar de seguir fotógrafos
- Curtir e descurtir fotografias
- Sistema de comentários nas fotos
- Edição e exclusão de comentários próprios

### 🔍 Descoberta e Busca

- Busca por fotógrafos por username
- Listagem de seguidores e seguindo
- Visualização de perfis públicos
- Sistema de sugestões de tags

### 👨‍💼 Painel Administrativo

- Gestão de usuários (bloqueio/desbloqueio)
- Controle de comentários
- Geração de relatórios em PDF
- Listagem completa de fotógrafos

### 📊 Relatórios

- Geração de PDF com comentários de fotos
- Estatísticas de curtidas e comentários

## 🛠️ Tecnologias Utilizadas

### Backend

- **Java 17** - Linguagem de programação
- **Spring Boot 3.3.5** - Framework principal
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **Hibernate** - ORM
- **PostgreSQL** - Banco de dados
- **Lombok** - Redução de boilerplate
- **OpenPDF** - Geração de documentos PDF

### Frontend

- **Thymeleaf** - Template engine
- **Tailwind CSS** - Framework CSS
- **jQuery** - Manipulação DOM e AJAX
- **Material Tailwind** - Componentes UI

### Ferramentas de Build e Deploy

- **Maven** - Gerenciamento de dependências
- **Spring Boot DevTools** - Desenvolvimento

## 📋 Pré-requisitos

- Java 17 ou superior
- PostgreSQL 12 ou superior
- Maven 3.6 ou superior

## 🚀 Como Executar

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/FlashG.git
cd FlashG
```

### 2. Configure o banco de dados

Crie um banco PostgreSQL e configure as credenciais no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/FlashG
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Execute o projeto

```bash
# Usando Maven
./mvnw spring-boot:run

# Ou compilando primeiro
./mvnw clean install
java -jar target/FlashG-0.0.1-SNAPSHOT.jar
```

### 4. Acesse a aplicação

Abra seu navegador e acesse: `http://localhost:8080/FlashG`

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/edu/ifpb/pweb2/flashg/
│   │   ├── controller/        # Controladores REST
│   │   ├── entity/           # Entidades JPA
│   │   ├── repository/       # Repositórios de dados
│   │   ├── service/          # Lógica de negócio
│   │   ├── dtos/             # Data Transfer Objects
│   │   ├── exception/        # Exceções customizadas
│   │   └── FlashGApplication.java
│   └── resources/
│       ├── templates/        # Templates Thymeleaf
│       ├── static/          # Recursos estáticos
│       └── application.properties
└── test/                    # Testes unitários
```

## 🎨 Interface

A aplicação possui uma interface moderna e responsiva construída com Tailwind CSS, oferecendo:

- **Design Clean**: Interface minimalista focada no conteúdo
- **Tema Profissional**: Cores e tipografia adequadas para fotógrafos
- **Experiência Fluida**: Navegação intuitiva e interações suaves

## 🔧 Configuração de Desenvolvimento

### Variáveis de Ambiente

Para deployment em produção, configure as seguintes variáveis:

```bash
DB_HOST=localhost
DB_PORT=5432
DB_DATABASE=FlashG
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
```

## 📝 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

