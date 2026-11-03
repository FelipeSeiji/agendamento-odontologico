# Agendamento Odontológico

API Spring Boot com Java 21, MySQL, Flyway e autenticação JWT. Esta pasta deve ser a raiz do repositório: `pom.xml`, `Dockerfile`, `.mvn` e `.github` ficam no mesmo nível.

## Banco e variáveis

Crie um MySQL de laboratório no Aiven e copie host, porta, banco e usuário em **Overview > Quick connect**. Use as credenciais do seu serviço. O Flyway aplica os arquivos de `src/main/resources/db/migration` ao iniciar; não crie as tabelas manualmente.

Configure estas variáveis no ambiente em que a API ou os testes vão rodar:

| Variável | Valor |
| --- | --- |
| `DB_URL` | `jdbc:mysql://SEU_HOST:SUA_PORTA/defaultdb?sslMode=REQUIRED` |
| `DB_USERNAME` | Usuário do seu MySQL |
| `DB_PASSWORD` | Senha do seu MySQL |
| `JWT_SECRET` | Chave aleatória com pelo menos 256 bits |
| `PORT` | Opcional; padrão 8080. No contêiner, use 10000. |

`sslMode=REQUIRED` exige criptografia. Para verificar também a identidade do servidor, configure o certificado CA e `VERIFY_IDENTITY` conforme a documentação do Aiven.

Para gerar uma chave JWT no PowerShell:

```powershell
$jwtBytes = [byte[]]::new(32)
[System.Security.Cryptography.RandomNumberGenerator]::Fill($jwtBytes)
[Convert]::ToBase64String($jwtBytes)
```

Guarde os valores fora do código. `.env.example` contém somente exemplos; `.env` e `.env.*` estão ignorados pelo Git e pelo Docker.

## Rodar e testar no Windows

Use JDK 21. No mesmo PowerShell em que vai executar o Maven:

```powershell
$env:DB_URL = 'jdbc:mysql://SEU_HOST:SUA_PORTA/defaultdb?sslMode=REQUIRED'
$env:DB_USERNAME = 'SEU_USUARIO'
$env:DB_PASSWORD = 'SUA_SENHA'
$env:JWT_SECRET = 'SUA_CHAVE_ALEATORIA'
.\mvnw.cmd --batch-mode clean verify
.\mvnw.cmd spring-boot:run
```

O Spring não carrega `.env` automaticamente. Os comandos acima definem as variáveis para o processo atual. Os testes iniciam a aplicação, executam migrations e verificam a saúde pública e a proteção das rotas; use um banco de laboratório.

Confira `http://localhost:8080/actuator/health`: deve retornar HTTP 200 e status `UP`. Os endpoints `/actuator/health/liveness` e `/actuator/health/readiness` também aceitam acesso sem token. As rotas de negócio continuam exigindo autenticação, exceto `POST /login`.

## Docker

Com Docker Desktop em execução:

```powershell
docker build -t agendamento-odontologico .
Copy-Item .env.example .env
# Preencha .env com os valores do seu serviço antes de executar.
docker run --rm --name agendamento-odontologico --env-file .env -p 10000:10000 agendamento-odontologico
```

O Dockerfile compila com Maven e JDK 21 e executa com JRE 21. A construção da imagem pula a execução dos testes; execute `clean verify` separadamente. Confira `http://localhost:10000/actuator/health`.

## GitHub Actions

Publique o conteúdo desta pasta em um repositório seu, com branch `main`. Em **Settings > Secrets and variables > Actions**, cadastre os quatro **Repository secrets**: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`.

O workflow `.github/workflows/agendamento-odontologico-cicd.yml` roda em pushes para `main` ou manualmente em **Actions > Agendamento Odontologico CI/CD > Run workflow**. Ele valida os secrets, executa os testes em Java 21 e constrói a imagem Docker.

O workflow usa o Aiven de laboratório; as credenciais não são enviadas para o build Docker. O repositório deve incluir `.mvn/wrapper/maven-wrapper.properties`.

## Render

Crie **New > Web Service** com o seu repositório e configure:

| Campo | Valor |
| --- | --- |
| Name | `agendamento-odontologico` |
| Branch | `main` |
| Language / Runtime | `Docker` |
| Root Directory | Deixe vazio |
| Dockerfile Path | `./Dockerfile` |
| Docker Build Context | `.` |
| Health Check Path | `/actuator/health` |
| Auto-Deploy | `Off` para deixar o GitHub Actions controlar os próximos deploys |

Cadastre `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` e `JWT_SECRET` no ambiente do Render, além de `PORT=10000`. Os secrets do GitHub não são transferidos automaticamente. Escolha o plano gratuito se estiver disponível e revise os valores antes de criar o serviço.

Como alternativa ao cadastro manual, `render.yaml` contém um Blueprint com plano `free` e solicita as quatro variáveis. Revise as opções no painel antes de confirmar a criação.

Após a primeira implantação, confira `https://SEU_SERVICO.onrender.com/actuator/health`.

## Deploy após o CI

1. No Render, copie **Settings > Deploy Hook**.
2. No GitHub, cadastre esse endereço no secret `RENDER_DEPLOY_HOOK_URL`.
3. No GitHub, em **Secrets and variables > Actions > Variables**, crie `DEPLOY_ENABLED` com valor `true`.
4. Faça um push para `main` ou execute o workflow manualmente na `main`.

O job `deploy` depende do sucesso de `test-and-build` e solicita ao Render o mesmo commit validado. O hook confirma que o deploy foi acionado; acompanhe o término e o endpoint de saúde no Render. Sem `DEPLOY_ENABLED=true`, somente o CI roda.

## Referências

- [Spring Boot Actuator](https://docs.spring.io/spring-boot/reference/actuator/endpoints.html)
- [Maven Wrapper](https://maven.apache.org/tools/wrapper/)
- [Deploy hooks do Render](https://render.com/docs/deploy-hooks)
- [Blueprints do Render](https://render.com/docs/blueprint-spec)
