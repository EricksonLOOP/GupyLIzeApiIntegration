# GupyLizeIntegration

O projeto **GupyLizeIntegration** é uma integração entre as plataformas Gupy e LizeEdu. O objetivo deste projeto é fornecer serviços que permitem o gerenciamento de testes, resultados de exames e candidatos. Ele utiliza a API da plataforma LizeEdu para recuperar e registrar informações sobre candidatos e seus resultados de exames, além de permitir a criação de usuários na plataforma.

## Funcionalidades

- **Recuperação de Testes:** Obtém a lista de testes disponíveis na plataforma LizeEdu, com a capacidade de filtrar por nome.
- **Registro de Candidatos:** Cria ou encontra um usuário na plataforma LizeEdu e associa a um teste específico.
- **Recuperação de Resultados de Testes:** Obtém os resultados dos testes de um candidato baseado no seu ID.

## Estrutura do Projeto

O projeto é composto por dois serviços principais: `TestServices` e `UserServices`.

### TestServices

O serviço `TestServices` é responsável por interagir com a API da LizeEdu para recuperar testes e resultados de exames.

- `RecuperarTestes(Integer limit, Integer offset)`: Recupera uma lista de testes filtrados por nome.
- `PegarResultadosDoUsuarioID(String idResult)`: Obtém os resultados de um candidato específico baseado no seu ID.

### UserServices

O serviço `UserServices` gerencia a criação e o registro de candidatos na plataforma LizeEdu.

- `encontrarOuCriarUsuario(BodyCandidateRegistration bodyCandidateRegistration)`: Cria ou encontra um usuário e o associa a um teste específico.

## Dependências

- **Spring Boot:** Framework usado para construir a aplicação.
- **OkHttp:** Biblioteca utilizada para fazer requisições HTTP para a API LizeEdu.
- **JSON:** Biblioteca para manipulação de dados JSON.

## Como Rodar o Projeto

### Pré-requisitos

- JDK 11 ou superior
- Apache Maven ou Gradle
- Token de acesso válido para a API LizeEdu

### Instruções

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/GupyLizeIntegration.git
   ```cd GupyLizeIntegration
   ```mvn install
   ``mvn spring-boot:run

