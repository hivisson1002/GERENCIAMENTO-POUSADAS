# 🏨 Sistema de Gerenciamento de Pousadas

Sistema completo para gerenciamento de pousadas desenvolvido em Java, com banco de dados PostgreSQL e interface via terminal.

## 📋 Descrição

Aplicação que permite o gerenciamento completo de pousadas, incluindo cadastro de estabelecimentos, quartos, funcionários, clientes e reservas. O sistema utiliza arquitetura em camadas (DAO, BO, DTO) e segue boas práticas de desenvolvimento orientado a objetos.

## 🛠️ Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **PostgreSQL** - Banco de dados relacional
- **JDBC** - Conexão com banco de dados
- **JUnit 5** - Testes unitários
- **Maven/Gradle** - Gerenciamento de dependências

## 📁 Estrutura do Projeto

```
GERENCIAMENTO-POUSADAS/
├── src/
│   ├── bo/              # Business Objects (regras de negócio)
│   │   ├── PousadaBO.java
│   │   └── QuartoBO.java
│   ├── conexao/         # Gerenciamento de conexão com BD
│   │   └── Conexao.java
│   ├── dao/             # Data Access Objects (acesso a dados)
│   │   ├── IDAO.java
│   │   ├── BaseDAO.java
│   │   ├── PousadaDAO.java
│   │   ├── QuartoDAO.java
│   │   ├── PessoaDAO.java
│   │   ├── PessoaFisicaDAO.java
│   │   ├── FuncionarioDAO.java
│   │   └── ReservaDAO.java
│   ├── dto/             # Data Transfer Objects (entidades)
│   │   ├── Pousada.java
│   │   ├── Quarto.java
│   │   ├── QuartoStandard.java
│   │   ├── QuartoDeluxe.java
│   │   ├── QuartoPresidencial.java
│   │   ├── IQuartoLuxo.java
│   │   ├── Pessoa.java
│   │   ├── PessoaFisica.java
│   │   ├── Funcionario.java
│   │   └── Reserva.java
│   ├── exception/       # Exceções customizadas
│   │   ├── DadosInvalidosException.java
│   │   ├── ValidacaoException.java
│   │   └── EntidadeNaoEncontradaException.java
│   ├── main/            # Classe principal
│   │   └── Main.java
│   ├── test/            # Testes unitários JUnit
│   │   ├── TestePousadaBOJUnit.java
│   │   ├── TesteQuartoBOJUnit.java
│   │   ├── TestePessoaDAOJUnit.java
│   │   ├── TestePessoaFisicaDAOJUnit.java
│   │   └── TesteReservaDAO.java
│   └── db/              # Scripts SQL
│       └── postgree-pousda.sql
├── lib/                 # Bibliotecas externas
│   ├── postgresql-42.7.3.jar
│   └── junit-platform-console-standalone-1.9.3.jar
├── bin/                 # Classes compiladas
└── LimparBanco.java     # Utilitário para resetar banco de dados
```

## 🎯 Funcionalidades

### 🏠 Gerenciamento de Pousadas
- Cadastrar, listar, buscar, atualizar e deletar pousadas
- Validação de dados (nome, telefone, cidade, estado, estrelas)
- Estatísticas (média de estrelas, busca por classificação)

### 🛏️ Gerenciamento de Quartos
- Três tipos de quartos: Standard, Deluxe e Presidencial
- Quartos de luxo com amenidades especiais (jacuzzi, sala de estar)
- Cálculo automático de valores com descontos progressivos
- Validação de capacidade (1-10 camas) e valores (R$ 50 - R$ 10.000)

### 👥 Gerenciamento de Pessoas
- Cadastro de pessoas físicas e funcionários
- Validação de CPF e dados obrigatórios
- Relacionamento com reservas e pousadas

### 🗓️ Gerenciamento de Reservas
- Criar, consultar, atualizar e cancelar reservas
- Verificação de disponibilidade de quartos
- Controle por pousada e por quarto
- Status de reservas (Sim/Não)

### 🎨 Funcionalidades do Sistema
- Interface interativa via terminal com menus
- Validação imediata de entradas (campos obrigatórios, formatos)
- Mensagens de erro claras e amigáveis
- Tratamento robusto de exceções

## 🗄️ Banco de Dados

### Estrutura das Tabelas

- **pousada**: Dados dos estabelecimentos
- **quarto**: Informações dos quartos (tipos, valores, amenidades)
- **pessoa**: Dados básicos de pessoas
- **pessoa_fisica**: Extensão com CPF e sexo
- **funcionario**: Funcionários vinculados às pousadas
- **reserva**: Controle de reservas e ocupação

### Relacionamentos
- Quarto → Pousada (FK: qua_pou)
- PessoaFisica → Pessoa (FK: pf_usuario)
- Funcionario → Pessoa (FK: func_usuario)
- Reserva → Pousada (FK: res_pou)
- Reserva → Quarto (FK: res_qua)
- Reserva → Pessoa (FK: res_usuario)

## 🚀 Como Executar

### 1. Pré-requisitos
- Java JDK 21 ou superior
- PostgreSQL instalado e configurado
- Git (para clonar o repositório)

### 2. Configurar Banco de Dados

```sql
-- Criar banco de dados
CREATE DATABASE gerenciamento_pousadas;

-- Executar script SQL
\i src/db/postgree-pousda.sql
```

### 3. Configurar Conexão

Edite o arquivo `src/conexao/Conexao.java` com suas credenciais:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/gerenciamento_pousadas";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

### 4. Compilar

```powershell
javac -d bin -cp "lib/postgresql-42.7.3.jar" src/**/*.java
```

### 5. Executar

```powershell
java -cp "bin;lib/postgresql-42.7.3.jar" main.Main
```

## 🧪 Executar Testes

### Todos os testes
```powershell
java -jar lib/junit-platform-console-standalone-1.9.3.jar --classpath bin --classpath lib/postgresql-42.7.3.jar --scan-classpath --disable-banner
```

### Teste específico
```powershell
java -jar lib/junit-platform-console-standalone-1.9.3.jar --classpath bin --classpath lib/postgresql-42.7.3.jar --select-class test.TestePousadaBOJUnit --disable-banner
```

### Limpar banco para testes
```powershell
javac -d bin -cp "bin;lib/postgresql-42.7.3.jar" src/LimparBanco.java
java -cp "bin;lib/postgresql-42.7.3.jar" LimparBanco
```

## 📊 Cobertura de Testes

- ✅ **59 testes unitários** implementados
- ✅ **100% de sucesso** na última execução
- Cobertura: DAO (Pessoa, PessoaFisica, Reserva), BO (Pousada, Quarto)

### Resumo dos Testes
- TestePessoaDAOJUnit: 8 testes
- TestePessoaFisicaDAOJUnit: 10 testes
- TesteReservaDAO: 8 testes
- TestePousadaBOJUnit: 15 testes
- TesteQuartoBOJUnit: 18 testes

## 🏗️ Arquitetura

### Camada DTO (Data Transfer Objects)
Entidades que representam os dados do sistema, com validações básicas.

### Camada DAO (Data Access Objects)
Responsável pela comunicação com o banco de dados. Todas as classes DAO implementam a interface `IDAO<T>` que define operações CRUD.

### Camada BO (Business Objects)
Contém as regras de negócio e validações complexas antes de persistir os dados.

### Camada de Apresentação
Interface via terminal (`Main.java`) com menus interativos e validação de entrada.

## 🔒 Validações Implementadas

### Pousada
- Nome: mínimo 3 caracteres
- Cidade: mínimo 3 caracteres
- Estado: exatamente 2 letras
- Telefone: mínimo 8 dígitos
- Estrelas: 1 a 5

### Quarto
- Nome: mínimo 3 caracteres
- Camas: 1 a 10
- Valor: R$ 50 a R$ 10.000
- Pousada deve existir

### Pessoa/Funcionário
- Usuário: mínimo 3 caracteres (chave única)
- Nome: mínimo 3 caracteres
- Telefone: mínimo 8 dígitos
- Sexo: M ou F
- CPF: apenas números

## 💡 Regras de Negócio

### Descontos em Reservas
- 5+ dias: 10% de desconto
- 10+ dias: 15% de desconto

### Quartos de Luxo
- Deluxe: adicional por jacuzzi
- Presidencial: adicional por jacuzzi + sala de estar

### Integridade Referencial
- Não é possível deletar pousadas com quartos/reservas
- Deleção de pessoa física remove automaticamente o registro de pessoa
- Validação de FK antes de inserção

## 👥 Autor

Desenvolvido como projeto acadêmico de Programação Orientada a Objetos.

## 📄 Licença

Este projeto é de uso acadêmico e educacional.
