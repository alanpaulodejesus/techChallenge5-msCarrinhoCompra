# Microserviço responsável pela parte de carrinho de compras do sistema.

O projeto msCarrinhoCompra é um microserviço dedicado à inclusão e remoção de itens ao carrinho pelo usuário. Desenvolvido usando o framework Spring Boot, o sistema fornece funcionalidades para criar um carrinho relacionado ao usuário, adicionar e remover itens ao carrinho. A aplicação possui uma funcionalidade de alteração de status para o envio dos itens para o pagamento.

# Funcionalidades

- Cadastro de carrinho: Criar um carrinho relacionado ao usuário.
- Adicionar itens ao carrinho: Permite usuário adicionar itens de compras do seu carrinho.
- Remoção de itens ao carrinho: Permite usuário remover itens de compras do seu carrinho.
- Alteração de status do carrinho: Finalização de seleção de items do carrinho e envio de informações para pagamento.

# Requisitos

- Java 17+
- Spring Boot 3.x
- Maven 3.x

# Estrutura do Projeto

- Controller: Camada responsável por gerenciar as requisições HTTP relacionadas a criação de carrinho, adicionamento e remoção de itens.
- Service: Camada onde está a lógica de negócio para permitindo validar usuário, e adicionamento e remoção de itens.
- Repository: Camada de acesso a dados utilizando WebFlux, responsável pela persistência e recuperação de informações sobre carrinho e itens.
- Exception Handling: Implementação de tratamento de exceções personalizadas, como UserNotFoundException, para fornecer mensagens sobre a identificação destes usuários.
- Model: Camada que define a estrutura de dados para a entidade como Cart e CartItem.

# Configuração
### Clone o repositório:
- git clone https://github.com/henriquegardini/msCarrinhoCompra.git

### Accesse o diretório do projeto:
- cd msCarrinhoCompra

### Instale as dependências do Maven:
- mvn clean install

- Configure o banco de dados no arquivo application.properties ou application.yml.

### Execute a aplicação:

- mvn spring-boot:run

### Swagger:

- Para acessar a documentação do Swagger acesse: http://localhost:8083/swagger-ui/index.html
