# 🚀 Desafio — Sistema de Controle Acadêmico

# 📚 Sumário

- [🎯 Objetivo](#-objetivo)
- [🏗️ Arquitetura do projeto](#️-arquitetura-do-projeto)
- [👨‍🎓 Entidades do sistema](#-entidades-do-sistema)
- [🗄️ Banco de dados](#️-banco-de-dados)
- [👨‍🎓 Cadastro de alunos](#-cadastro-de-alunos)
- [👨‍🏫 Cadastro de professores](#-cadastro-de-professores)
- [📚 Cadastro de disciplinas](#-cadastro-de-disciplinas)
- [📝 Matrículas](#-matrículas)
- [🔎 Consultas utilizando JOIN](#-consultas-utilizando-join)
- [🔌 JDBC](#-jdbc)
- [🔧 ConnectionFactory](#-connectionfactory)
- [⚙️ Services](#️-services)
- [🗃️ DAOs](#️-daos)
- [💻 Interface no console](#-interface-no-console)
- [📦 Maven](#-maven)
- [📁 Organização esperada](#-organização-esperada)
- [📄 Entrega do projeto](#-entrega-do-projeto)
- [🎤 Apresentação](#-apresentação)
- [⭐ Funcionalidades opcionais](#-funcionalidades-opcionais)
- [✅ Checklist de entrega](#-checklist-de-entrega)
- [🏅 Hall da Fama](#-hall-da-fama)
- [🏆 Objetivo final](#-objetivo-final)

## 🎯 Objetivo

Desenvolver uma aplicação Java para **controle acadêmico**, executada no console, utilizando banco de dados PostgreSQL e acesso aos dados por meio de JDBC.

O sistema deverá permitir o cadastro e gerenciamento de:

- Alunos;
- Professores;
- Disciplinas;
- Matrículas.

O projeto deverá aplicar os conceitos de Java, orientação a objetos, JDBC, SQL e arquitetura em camadas apresentados durante o curso.

### Tecnologias obrigatórias

- Java 21
- Maven
- PostgreSQL
- JDBC
- JLine
- Lombok
- Eclipse IDE

---

# 🏗️ Arquitetura do projeto

O projeto deverá utilizar uma arquitetura organizada em camadas:

```text
src/main/java
└── br.com.fuctura.academico
    │
    ├── application
    │   └── Application.java
    │
    ├── console
    │   ├── ConsoleUI.java
    │   └── MenuPrincipal.java
    │
    ├── controlador
    │   ├── AlunoControlador.java
    │   ├── ProfessorControlador.java
    │   ├── DisciplinaControlador.java
    │   └── MatriculaControlador.java
    │
    ├── entity
    │   ├── Aluno.java
    │   ├── Professor.java
    │   ├── Disciplina.java
    │   └── Matricula.java
    │
    ├── service
    │   ├── AlunoService.java
    │   ├── ProfessorService.java
    │   ├── DisciplinaService.java
    │   └── MatriculaService.java
    │
    ├── dao
    │   ├── AlunoDAO.java
    │   ├── ProfessorDAO.java
    │   ├── DisciplinaDAO.java
    │   └── MatriculaDAO.java
    │
    └── infrastructure
        ├── DatabaseConfig.java
        └── ConnectionFactory.java
```

## Responsabilidade de cada pacote

| Pacote | Responsabilidade |
|---|---|
| `application` | Inicialização da aplicação |
| `console` | Interação com o usuário pelo terminal |
| `controlador` | Coordenação das ações solicitadas pelo usuário e comunicação com a camada `service` |
| `entity` | Classes que representam as entidades do sistema |
| `service` | Regras e operações do sistema |
| `dao` | Acesso e operações no banco de dados |
| `infrastructure` | Configuração e conexão com o PostgreSQL |


No projeto atual, o controlador deverá receber a solicitação da interface, organizar os dados necessários e chamar o `service` correspondente.

Fluxo esperado:

```text
Usuário
   ↓
Console
   ↓
Controlador
   ↓
Service
   ↓
DAO
   ↓
JDBC
   ↓
PostgreSQL
```

Por exemplo:

```text
AlunoControlador
      ↓
AlunoService
      ↓
AlunoDAO
      ↓
PostgreSQL
```

O controlador **não deverá acessar diretamente o banco de dados**. A comunicação com o banco continuará sendo responsabilidade do `DAO`.

### Exemplo conceitual

```java
public class AlunoControlador {

    private final AlunoService alunoService;

    public AlunoControlador(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    public void cadastrarAluno(Aluno aluno) {
        alunoService.cadastrar(aluno);
    }

    public void listarAlunos() {
        alunoService.listar();
    }
}
```

A implementação poderá variar conforme a solução do aluno, mas a responsabilidade da camada deverá ser preservada.

> **Importante:** o pacote `controlador` neste projeto não significa que o aluno deverá utilizar Spring. Ele existe para introduzir a separação de responsabilidades que será utilizada posteriormente no módulo de **Spring MVC**.

| `application` | Inicialização da aplicação |
| `console` | Interação com o usuário pelo terminal |
| `controlador` | Coordena as requisições/operações da aplicação e faz a ponte entre a interface e os serviços |
| `entity` | Classes que representam as entidades do sistema |
| `service` | Regras e operações do sistema |
| `dao` | Acesso e operações no banco de dados |
| `infrastructure` | Configuração e conexão com o PostgreSQL |

### 📌 Entidades

As classes:

- `Aluno`
- `Professor`
- `Disciplina`
- `Matricula`

deverão ficar no pacote:

```text
entity
```

Exemplo:

```java
package br.com.fuctura.academico.entity;

public class Aluno {

    private String matricula;
    private String nome;
    private Integer idade;
    private String celular;

    // construtores, getters e setters
}
```

---

# 👨‍🎓 Entidades do sistema

O sistema deverá trabalhar, no mínimo, com as seguintes entidades:

## Aluno

Representa um aluno da instituição.

Exemplo de atributos:

```text
matricula
nome
idade
celular
```

## Professor

Representa um professor da instituição.

Exemplo de atributos:

```text
matricula
nome
telefone
```

## Disciplina

Representa uma disciplina oferecida pela instituição.

Exemplo de atributos:

```text
codigo
nome
carga_horaria
professor
```

A disciplina deverá estar associada a um professor.

## Matricula

Representa a matrícula de um aluno em uma disciplina.

Exemplo de atributos:

```text
id
data_matricula
aluno
disciplina
```

---

# 🗄️ Banco de dados

O banco de dados deverá ser desenvolvido em **PostgreSQL**.

As tabelas principais serão:

```text
aluno
professor
disciplina
matricula
```

Os relacionamentos deverão utilizar **chaves primárias e chaves estrangeiras**.

A estrutura deverá representar:

```text
PROFESSOR
    │
    │ 1:N
    ▼
DISCIPLINA
    │
    │ 1:N
    ▼
MATRICULA
    ▲
    │ N:1
    │
ALUNO
```

A tabela `matricula` deverá relacionar alunos e disciplinas.

---

# 👨‍🎓 Cadastro de alunos

O sistema deverá permitir, no mínimo:

- Cadastrar aluno;
- Listar alunos;
- Buscar aluno;
- Alterar aluno;
- Excluir aluno.

Exemplo de menu:

```text
===== ALUNOS =====

1 - Cadastrar aluno
2 - Listar alunos
3 - Buscar aluno
4 - Alterar aluno
5 - Excluir aluno
0 - Voltar
```

---

# 👨‍🏫 Cadastro de professores

O sistema deverá permitir:

- Cadastrar professor;
- Listar professores;
- Buscar professor;
- Alterar professor;
- Excluir professor.

Exemplo:

```text
===== PROFESSORES =====

1 - Cadastrar professor
2 - Listar professores
3 - Buscar professor
4 - Alterar professor
5 - Excluir professor
0 - Voltar
```

---

# 📚 Cadastro de disciplinas

O sistema deverá permitir:

- Cadastrar disciplina;
- Listar disciplinas;
- Buscar disciplina;
- Alterar disciplina;
- Excluir disciplina.

A disciplina deverá estar associada a um professor.

Exemplo:

```text
===== DISCIPLINAS =====

1 - Cadastrar disciplina
2 - Listar disciplinas
3 - Buscar disciplina
4 - Alterar disciplina
5 - Excluir disciplina
0 - Voltar
```

---

# 📝 Matrículas

O sistema deverá possuir funcionalidades relacionadas às matrículas.

No mínimo:

- Matricular aluno em uma disciplina;
- Cancelar matrícula;
- Listar disciplinas de um aluno;
- Listar alunos de uma disciplina;
- Consultar informações acadêmicas do aluno.

Exemplo:

```text
===== MATRÍCULAS =====

1 - Matricular aluno
2 - Cancelar matrícula
3 - Disciplinas do aluno
4 - Alunos da disciplina
5 - Consultar situação acadêmica
0 - Voltar
```

---

# 🔎 Consultas utilizando JOIN

O projeto deverá demonstrar a utilização de `JOIN` no PostgreSQL.

Por exemplo, o sistema deverá permitir consultar as disciplinas nas quais determinado aluno está matriculado.

A consulta deverá relacionar as tabelas necessárias para obter informações como:

```text
Aluno
Disciplina
Professor
Data da matrícula
```

Também deverá existir pelo menos uma consulta envolvendo **três ou mais tabelas**.

Exemplo conceitual:

```text
ALUNO
  ↓
MATRICULA
  ↓
DISCIPLINA
  ↓
PROFESSOR
```

O objetivo é demonstrar que o aluno consegue utilizar os relacionamentos entre tabelas para produzir informações úteis para o sistema.

---

# 🔌 JDBC

O acesso ao PostgreSQL deverá ser realizado utilizando **JDBC**.

O projeto deverá utilizar os principais recursos apresentados durante o curso:

- `DriverManager`
- `Connection`
- `PreparedStatement`
- `ResultSet`
- `SQLException`

As operações de banco deverão ser realizadas utilizando `PreparedStatement`.

Exemplo:

```java
String sql = """
    SELECT *
    FROM aluno
    WHERE matricula = ?
    """;

PreparedStatement statement =
        connection.prepareStatement(sql);

statement.setString(1, matricula);

ResultSet resultSet =
        statement.executeQuery();
```

---

# 🔧 ConnectionFactory

O projeto deverá possuir uma classe responsável por criar conexões com o banco.

Exemplo:

```text
infrastructure
└── ConnectionFactory.java
```

A aplicação deverá evitar espalhar código de conexão pelo projeto.

Os DAOs deverão obter a conexão por meio da infraestrutura definida para o projeto.

---

# ⚙️ Services

As operações do sistema deverão passar pela camada `service`.

Exemplo:

```text
AlunoService
ProfessorService
DisciplinaService
MatriculaService
```

A camada de serviço será responsável por organizar as operações e regras do sistema antes que elas sejam executadas pelo DAO.

Fluxo esperado:

```text
Console
   ↓
Controlador
   ↓
Service
   ↓
DAO
   ↓
JDBC
   ↓
PostgreSQL
```

---

# 🗃️ DAOs

Cada entidade deverá possuir seu DAO correspondente.

Exemplo:

```text
Aluno       → AlunoDAO
Professor   → ProfessorDAO
Disciplina  → DisciplinaDAO
Matricula   → MatriculaDAO
```

Os DAOs serão responsáveis pelas operações de persistência.

Exemplo:

```java
public class AlunoDAO {

    public void salvar(Aluno aluno) {
        // INSERT
    }

    public List<Aluno> listar() {
        // SELECT
    }

    public Aluno buscar(String matricula) {
        // SELECT ... WHERE
    }

    public void atualizar(Aluno aluno) {
        // UPDATE
    }

    public void excluir(String matricula) {
        // DELETE
    }
}
```

---

# 💻 Interface no console

A aplicação deverá possuir uma interface organizada para utilização pelo terminal.

O projeto deverá utilizar **JLine** para melhorar a interação com o usuário.

O menu principal poderá seguir o seguinte modelo:

```text
=================================
     SISTEMA ACADÊMICO
=================================

1 - Alunos
2 - Professores
3 - Disciplinas
4 - Matrículas
0 - Sair

Escolha uma opção:
```

O aluno deverá organizar a navegação de forma que o usuário consiga utilizar o sistema sem precisar conhecer SQL.

---

# 📦 Maven

O projeto deverá ser configurado como um projeto Maven.

O arquivo `pom.xml` deverá conter as dependências utilizadas no projeto, incluindo:

- PostgreSQL JDBC;
- JLine;
- Lombok.

O projeto deverá utilizar Java 21.

---

# 📁 Organização esperada

Ao final, espera-se uma estrutura semelhante a:

```text
projeto-academico/
│
├── pom.xml
├── README.md
├── database.sql
│
└── src/
    └── main/
        └── java/
            └── br/
                └── com/
                    └── fuctura/
                        └── academico/
                            │
                            ├── application/
                            │
                            ├── console/
                            │
                            ├── controlador/
                            │
                            ├── entity/
                            │
                            ├── service/
                            │
                            ├── dao/
                            │
                            └── infrastructure/
```

---

# 📄 Entrega do projeto

O aluno deverá entregar:

## 1. Código-fonte

Todo o projeto Maven contendo o código Java.

## 2. Script do banco

Um arquivo:

```text
database.sql
```

contendo os comandos necessários para criar a estrutura do banco.

O arquivo deverá conter, no mínimo:

- criação das tabelas;
- chaves primárias;
- chaves estrangeiras;
- dados iniciais para teste, quando necessário.

## 3. README.md

O projeto deverá possuir um `README.md` contendo:

- Nome do projeto;
- Descrição;
- Tecnologias utilizadas;
- Como configurar o banco;
- Como configurar a aplicação;
- Como executar o projeto;
- Informações necessárias para conexão com o PostgreSQL.

## 4. Versionamento

O projeto deverá ser entregue todo versionado no Github.

---

# 🎤 Apresentação

Durante a apresentação, o aluno poderá ser questionado sobre as decisões tomadas no projeto.

Entre os pontos que deverão ser compreendidos estão:

## Java

- O que é uma classe?
- O que é um objeto?
- Qual a função das entidades?
- Por que utilizar encapsulamento?

## Arquitetura

- Qual a responsabilidade do pacote `entity`?
- Qual a responsabilidade do `service`?
- Qual a responsabilidade do `dao`?
- Qual a função da camada `infrastructure`?
- Por que separar essas responsabilidades?

## Banco de dados

- O que é uma chave primária?
- O que é uma chave estrangeira?
- Qual o relacionamento entre aluno e disciplina?
- Qual a função da tabela `matricula`?

## SQL

- Qual a diferença entre `INSERT`, `SELECT`, `UPDATE` e `DELETE`?
- O que é um `JOIN`?
- Por que utilizar `PreparedStatement`?

## JDBC

- Qual a função de `Connection`?
- Qual a função de `PreparedStatement`?
- Qual a função de `ResultSet`?
- Como a aplicação Java se comunica com o PostgreSQL?

---

# ⭐ Funcionalidades Extras Obrigatórias

Os sistema deve apresentar as funcionalidades abaixo:

- Pesquisa aluno por nome;
- Ordenação a consulta por nome do aluno;

Essas funcionalidades são opcionais e não substituem os requisitos obrigatórios.

---

# ✅ Checklist de entrega

Antes de entregar, verifique:

- [ ] Projeto Maven criado
- [ ] Java 21 configurado
- [ ] PostgreSQL configurado
- [ ] JDBC funcionando
- [ ] JLine configurado
- [ ] Lombok configurado
- [ ] Pacote `entity` criado
- [ ] Pacote `controlador` criado
- [ ] Controladores implementados
- [ ] `Aluno` implementado
- [ ] `Professor` implementado
- [ ] `Disciplina` implementado
- [ ] `Matricula` implementado
- [ ] DAOs implementados
- [ ] Services implementados
- [ ] ConnectionFactory implementada
- [ ] CRUD de alunos
- [ ] CRUD de professores
- [ ] CRUD de disciplinas
- [ ] Operações de matrícula
- [ ] Consultas utilizando JOIN
- [ ] Interface de console funcionando
- [ ] `database.sql` criado
- [ ] `README.md` criado
- [ ] Projeto testado do início ao fim

---

# 🏆 Objetivo final

Ao concluir o projeto, o aluno deverá ser capaz de demonstrar que consegue construir uma aplicação Java completa, conectada a um banco PostgreSQL, utilizando JDBC e organizada em camadas.


## 🏆 Hall da Fama

![Hall da Fama](/image_5a7f63b7.jpg)

Alunos que concluíram o Projeto de Conclusão do Curso.

### Turma 2026.2 - j2290826



| # | Aluno | Projeto | Data de conclusão |
|---:|---|---|---|
