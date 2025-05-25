# Gestor de Tarefas

Sistema web para cadastro, listagem e gerenciamento de tarefas desenvolvido em Java usando Jakarta EE (JSF, CDI, JPA), PrimeFaces e Hibernate. O projeto é empacotado como arquivo WAR e pode ser implantado em servidores de aplicação como WildFly (que eu utilizei) ou Payara.

---

## Funcionalidades

- Cadastro de tarefas com descrição e data de criação.
- Listagem das tarefas cadastradas.
- Edição e remoção de tarefas.
- Interface web amigável com PrimeFaces.
- Persistência dos dados em banco PostgreSQL via JPA/Hibernate.

---

## Tecnologias utilizadas

- Java 17
- Jakarta EE (JSF 3.0, CDI 4.0, JPA 3.0, Servlet 5.0)
- Hibernate ORM 6.x
- PrimeFaces 14 (versão Jakarta)
- PostgreSQL (driver JDBC)
- Maven para gerenciamento de dependências e build
- Servidor de aplicação WildFly (ou similar)

---

## Pré-requisitos

- JDK 17 instalado ([baixar](https://adoptium.net/))
- Maven instalado ([baixar](https://maven.apache.org/download.cgi))
- PostgreSQL instalado e rodando
- Servidor WildFly (recomendável) ou Payara para deploy do WAR

---

## Configuração do banco de dados

1. Crie um banco de dados no PostgreSQL, por exemplo `gestortarefas`.
2. Configure usuário e senha com permissões para o banco.
3. Ajuste o arquivo `persistence.xml` na pasta `src/main/resources/META-INF` para apontar para seu banco, alterando as propriedades:

```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/gestortarefas"/>
<property name="jakarta.persistence.jdbc.user" value="seu_usuario"/>
<property name="jakarta.persistence.jdbc.password" value="sua_senha"/>
```

## Como rodar o projeto

### Build do projeto

No terminal, na raiz do projeto, rode:

```
mvn clean package
```

Isso vai gerar o arquivo `gestor-tarefas.war` na pasta `target`.

### Deploy no servidor WildFly

1. Copie o arquivo WAR gerado (`gestor-tarefas.war`) para a pasta `standalone/deployments` do WildFly.
2. Inicie o servidor WildFly:

- No Windows:

```
standalone.bat
```

- No Linux/Mac:

```
./standalone.sh
```


3. O WildFly vai detectar o WAR automaticamente e realizar o deploy.

---

## Acessando a aplicação

Abra o navegador e acesse:

```
http://localhost:8080/gestor-tarefas/
```


Você verá a página inicial com opções para cadastrar novas tarefas e listar as existentes.

---

## Como usar

- **Cadastrar tarefa:** Vá para a página de cadastro, preencha a descrição e a data, e clique em salvar.
- **Listar tarefas:** Acesse a página de lista para ver todas as tarefas cadastradas.
- **Editar tarefa:** Na lista, clique em editar para modificar os dados da tarefa.
- **Excluir tarefa:** Na lista, clique em excluir para remover a tarefa.

---

## Estrutura do projeto

- `src/main/java`: código fonte Java (beans, DAO, entidades, conversores).
- `src/main/resources`: configurações e arquivos de recursos.
- `src/main/webapp`: arquivos web (XHTML, WEB-INF).
- `pom.xml`: configuração do Maven.
