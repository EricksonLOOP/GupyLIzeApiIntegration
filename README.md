
    <h1>GupyLizeIntegration</h1>
    <p>O projeto GupyLizeIntegration é uma integração entre as plataformas Gupy e LizeEdu. O objetivo deste projeto é fornecer serviços que permitem o gerenciamento de testes, resultados de exames e candidatos. Ele utiliza a API da plataforma LizeEdu para recuperar e registrar informações sobre candidatos e seus resultados de exames, além de permitir a criação de usuários na plataforma.</p>

    <h2>Funcionalidades</h2>
    <ul>
        <li><strong>Recuperação de Testes:</strong> Obtém a lista de testes disponíveis na plataforma LizeEdu, com a capacidade de filtrar por nome.</li>
        <li><strong>Registro de Candidatos:</strong> Cria ou encontra um usuário na plataforma LizeEdu e associa a um teste específico.</li>
        <li><strong>Recuperação de Resultados de Testes:</strong> Obtém os resultados dos testes de um candidato baseado no seu ID.</li>
    </ul>

    <h2>Estrutura do Projeto</h2>
    <p>O projeto é composto por dois serviços principais: <code>TestServices</code> e <code>UserServices</code>.</p>

    <h3>TestServices</h3>
    <p>O serviço <code>TestServices</code> é responsável por interagir com a API da LizeEdu para recuperar testes e resultados de exames.</p>
    <ul>
        <li><code>RecuperarTestes(Integer limit, Integer offset):</code> Recupera uma lista de testes filtrados por nome.</li>
        <li><code>PegarResultadosDoUsuarioID(String idResult):</code> Obtém os resultados de um candidato específico baseado no seu ID.</li>
    </ul>

    <h3>UserServices</h3>
    <p>O serviço <code>UserServices</code> gerencia a criação e o registro de candidatos na plataforma LizeEdu.</p>
    <ul>
        <li><code>encontrarOuCriarUsuario(BodyCandidateRegistration bodyCandidateRegistration):</code> Cria ou encontra um usuário e o associa a um teste específico.</li>
    </ul>

    <h2>Dependências</h2>
    <ul>
        <li><strong>Spring Boot:</strong> Framework usado para construir a aplicação.</li>
        <li><strong>OkHttp:</strong> Biblioteca utilizada para fazer requisições HTTP para a API LizeEdu.</li>
        <li><strong>JSON:</strong> Biblioteca para manipulação de dados JSON.</li>
    </ul>

    <h2>Como Rodar o Projeto</h2>
    <h3>Pré-requisitos</h3>
    <ul>
        <li>JDK 11 ou superior</li>
        <li>Apache Maven ou Gradle</li>
        <li>Token de acesso válido para a API LizeEdu</li>
    </ul>

    <h3>Instruções</h3>
    <ol>
        <li>Clone o repositório:</li>
        <pre><code>git clone https://github.com/seu-usuario/GupyLizeIntegration.git</code></pre>

        <li>Entre no diretório do projeto:</li>
        <pre><code>cd GupyLizeIntegration</code></pre>

        <li>Instale as dependências:</li>
        <pre><code>mvn install</code></pre>

        <li>Edite o código para adicionar seu token de API LizeEdu no arquivo <code>UserServicesImpl.java</code> nas requisições HTTP.</li>

        <li>Execute o projeto:</li>
        <pre><code>mvn spring-boot:run</code></pre>
    </ol>

    <h2>Exemplo de Requisição</h2>
    <h3>Criar ou Encontrar Usuário</h3>
    <p>O endpoint <code>encontrarOuCriarUsuario</code> cria ou encontra um usuário na plataforma LizeEdu. Exemplo de requisição:</p>
    <pre><code>
    POST /api/v2/students/
    Content-Type: application/json
    Authorization: Token SEU TOKEN LIZE AQUI

    {
        "email": "email@exemplo.com",
        "test_id": "12345",
        "name": "Nome do Candidato"
    }
    </code></pre>
    <p>Resposta de sucesso:</p>
    <pre><code>
    HTTP/1.1 201 Created
    Location: https://app.lizeedu.com.br/conta/sso?access_token=seu_token
    </code></pre>

    <h2>Como Contribuir</h2>
    <p>Contribuições são bem-vindas! Para contribuir com o projeto:</p>
    <ol>
        <li>Faça um fork do repositório.</li>
        <li>Crie uma branch com suas alterações: <code>git checkout -b minha-alteracao</code>.</li>
        <li>Faça commit das suas alterações: <code>git commit -am 'Minha alteração'</code>.</li>
        <li>Envie para o seu repositório: <code>git push origin minha-alteracao</code>.</li>
        <li>Abra um pull request.</li>
    </ol>

    <h2>Licença</h2>
    <p>Este projeto está licenciado sob a MIT License - veja o arquivo <code>LICENSE</code> para mais detalhes.</p>
