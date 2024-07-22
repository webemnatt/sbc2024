# Santander Bootcamp 2024

Java RESTFul criada para o bootcamp 2024.

## Diagrama de Classes

_User_ é uma entidade necessária e imprescindível nesse modelo de classes.
Seu relacionamento com _Account_ e _Card_ é de 1 : 1. Seu relacionamento com os demais, é de 1 : muitos.

```mermaid
classDiagram
    class User {
        - name: string
        - account: Account
        - features: Feature[]
        - card: Card
        - news: News[]
    }

    class Account {
        - number: string
        - agency: string
        - balance: BigDecimal
        - limit: BigDecimal
    }

    class Feature {
        - icon: string
        - description: string
    }

    class Card {
        - number: string
        - limit: BigDecimal
    }

    class News {
        - icon: string
        - description: string
    }

    User "1" *-- "1" Account
    User "1" *-- "n" Feature
    User "1" *-- "1" Card
    User "1" *-- "n" News
```

---

## Configuração

Para que o vscode siga o script do arquivo de configuração, 
1. configure a variável de ambiente no _launch.json_, 
    1.1. Se não existe o arquivo _launch.json_ dentro da pasta vscode:
        1.1.a. dê o comando ctrol shift p e digite: "Java: Create Launch Configuration"
        1.1.b. clique sobre a opção listada "launch.json" e em "java" como depurador.
        1.1.c. será criado o arquivo _launch.json_ dentro da pasta vscode com algumas configurações iniciais.
        ```
        {
            "type": "java",
            "name": "Application",
            "request": "launch",
            "mainClass": "project.my.sbc2024.Application",
            "projectName": "sbc2024"
         },
        ```
        1.1.d. adicione um par de chaves o env a seguir:

### ambiente dev ([h2.console](http://localhost:8080/h2-console))

```
    {
      "env": {
        "SPRING_PROFILES_ACTIVE": "dev",
      }
    },
```
Ao rodar o projeto novamente, será exibido algo como "Hibernate: drop"... as tabelas foram criadas no banco de dados local.

### ambiente prod (railway.app)

Para que o ambiente de prod rode, é preciso substituir a env acima pela debaixo:

As informações foram geradas sem cadastro no app. Dados disponíveis somente por 24h.
Opção selecionada: Postgres.
Informações disponíveis na aba "variables".
Visualização de tabelas na aba "data".

Substitua "localhost","pgport","mydatabase","myusername" e "mypassword" pelas informadas na página do railway ao gerar um novo Postgres.

```
{
        "env": {
            "PGHOST": "localhost",
            "PGPORT": "pgport",
            "PGDATABASE": "mydatabase",
            "PGUSER": "myusername",
            "PGPASSWORD": "mypassword"
        }
    }
```

## Acessando o h2-console no navegador

Insira o endereço `http://localhost:8080/h2-console`

## Dependência swagger

Para não precisar fazer CRUD via CURL. Tem interface gráfica para consumir os endpoints. Documenta, faz testes.

`http://localhost:8080/swagger-ui.html`

## Erro "Application run failed"

Caso ocorra o erro `ERROR 12634`, é necessário parar o uso da porta 8080.

`sudo netstat -tulnp | grep :8080` lista todas as portas utilizadas. A resposta será como `tcp        0      0 0.0.0.0:8080            0.0.0.0:*               OUÇA       ${PID}/docker-proxy `.
`sudo kill ${PID}`: encerra o uso.
