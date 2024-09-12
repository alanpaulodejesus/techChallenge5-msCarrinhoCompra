# Microserviço responsável pela parte de Carrinho de Compras.

## Descrição

O projeto **ms** é um microserviço dedicado ao carrinho de compras. Desenvolvido usando o framework Spring Boot, o sistema fornece funcionalidades para criar o carrinho e adicionar e remover itens pelo usuário, bem como possibilidade de alterar status e realizar consultas de carrinhos.

## Funcionalidades

- **Cadastro de Carrinho:** Cadastro de carrinho.
- **Adicionar itens do Carrinho:** Permite adicionar itens no carrinho a um usuário.
- **Excluir itens do Carrinho:** Permite remover itens no carrinho a um usuário.
- **Alteração de Status do Carrinho:** Permite alterar status de um carrinho declarando finalizada inclusão de itens.
- **Consulta de carrinhos:** Permite consulta itens por carrinho.

## Requisitos

- Java 17+
- Spring Boot 3.x
- Maven 3.x

## Estrutura do Projeto

- **Controller:** Camada responsável por gerenciar as requisições HTTP relacionadas a chamadas de cadastro de carrinho e adicionamento e remoção de itens, bem como listagem por carrinho e alteração de status.
- **Service:** Camada onde está a lógica de negócio para o gerenciamento de itens por carrinho, e mantém cada usuário em um carrinho enquanto não possui status finalizado.
- **Repository:** Camada de acesso a dados utilizando Spring WebFlux, responsável pela persistência e recuperação de informações sobre cart e itens no banco de dados.
- **Exception Handling:** Implementação de tratamento de exceções personalizadas, como UserNotFoundException, para fornecer mensagens claras e informativas sobre erros relacionados à validações de acessos aos usuários cadastrados e com token válidos.
- **Model:** Camada que define a estrutura de dados para a entidade Cart.

## Configuração

1. Clone o repositório:

   ```bash
   git clone https://github.com/henriquegardini/msCarrinhoCompra.git
    ```

2. Accesse o diretório do projeto:

   ```bash
   cd msCarrinhoCompra
   ```

3. Instale as dependências do Maven:

   ```bash
   mvn clean install
   ```

4. Configure o banco de dados no arquivo application.properties ou application.yml.

5. Execute a aplicação:

   ```bash
   mvn spring-boot:run
   ```

## Uso

### Endpoints disponíveis:

- **POST /cart/create/{userId}**

  Cadastro de carrinho a um usuário.

- **POST /cart/{userId}/add**

  Adiciona itens a um carrinho de um usuário.

- **POST /cart/{userId}/update-status**

  Altera status de um carrinho permitindo finalizar inserção de itens.

- **PUT /cart/{userId}/update-status**

  Altera status de um carrinho permitindo finalizar inserção de itens.

- **GET /cart/{id}**

  Retorna carrinhos por id.

- **DELETE /cart/{userId}/remove/{itemId}**

  Remove itens do carrinho de um usuário.

### Exemplo de Requisição:
**Swagger**:
```
http://localhost:8083/swagger-ui/index.html#/
```
**Para cada requisição no carrinho, é necessário informar o id do usuário e o token, do microserviço de login.
Execução pode ser realizada pelo Swagger.**

### Importante:
**Durante o desenvolvimento tivemos um problema na integração do microserviço do Carrinhos de Compras, 
com o microserviço de Gestão de Itens, devido incompatibilidade do Webflux (Mono e Flux). Para melhor 
visualização está sendo utilizado no carrinho um mock simulando dados dos itens no carrinho**

