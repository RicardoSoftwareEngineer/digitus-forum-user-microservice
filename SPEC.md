<!-- para IA. não é README de humano. -->
# SPEC — user

status: v0
sha: `e9b5485`
data: 2026-08-28

## Como usar
- Este arquivo é a fonte. Código ≠ spec → **bug de código**. Spec errada → Ricardo muda **este** arquivo, depois o código.
- IDs estáveis (`INV-` `DADOS-` `END-` `NÃO-` `GAP-`). Não apague ID; marque `revogado`.
- "achei bug" → cita INV/END. Se não existir, é GAP, não patch.
- "não estamos salvando X" → olha DADOS. Campo ausente = não é bug.
- "cadastrar campo X" → conflita se quebra INV/NÃO; senão vira GAP e só então código.
- GAP = pergunta aberta. Não trate GAP como regra.

## Papel
MS **interno** (porta `8083`). CRUD de User, verificação de email, reset de senha, chat. Sem auth HTTP (interno). Dono dos dados de pessoa.

## INV
- INV-USER-1: user persistido tem `id` (UUID), `name`, `email`, `password` (cifrada), `type` (default `client`), `deleted`.
- INV-USER-4: signup real = `validateEmail`. HTTP `/user/v1/create` é **stub** (não persiste). No signup, `name` nasce null; `type` = `client`.
- INV-USER-2: email único entre não-deletados.
- INV-USER-3: senha **não** é texto puro. Mecanismo atual = `Encryptors.text` reversível (observado). Spec de produto: senha não volta em JSON de API pública (borda). Interno ainda devolve entidade — GAP-PWD-JSON.
- INV-EV-1: cadastro público passa por email verification: envia código → valida código+senha → **cria** User.
- INV-EV-2: código vive em `EmailVerification` (`email`, `readableNumber`, `lastEmailSent`).
- INV-DEL-1: delete é soft (`deleted=true`) salvo spec futura.
- INV-CHAT-1: conversa pertence a `userId`. Mensagens salvam texto + metadados.

## NÃO
- NÃO-EXPOSE: não é API pública. Quem chama da internet é o firewall.
- NÃO-JWT
- NÃO-SHUTDOWN
- NÃO-FACTDADOS: tabela `FactDados` **não** faz parte do produto Digitus Forum (legado). Não evoluir.

## DADOS
| id | tabela | campos |
|---|---|---|
| DADOS-USER | User | id, name, email, password, type, deleted |
| DADOS-EV | EmailVerification | emailVerificationId, email, readableNumber, lastEmailSent |
| DADOS-SUBJ | ChatSubject | chatSubjectId, userId, name, privateOrPublic, status, lastUpdated, deleted |
| DADOS-MSG | ChatMessage | chatMessageId, chatSubjectId, userId, userName, userEmail, userType, message, status, position. `alignment` é calculado na leitura, **não** persiste. |

Não salva: recaptcha, token, perfil, curso.

## END
User: `/user/v1/create` (**stub, não salva**) · `/{id}/retrieve` · `/create/validateEmail` (**nome mentiroso: é login email+senha, não cria user**) · `/retrieve/byEmailAndPassword` · `/{id}/update` · `/{id}/delete`
Email: `/emailVerification/v1/sendValidationEmail` · `validateEmail` · `sendResetPasswordEmail` · `resetPassword`
Chat: `/user/v1/chat` · `conversations` · `conversation` · `sup` (interno; borda `/firewall/sup` **não** existe)
Health: `/user/v1/healthCheck`

`/user/v1/retrieve` (lista) está comentado — **não** está na spec.

## GAP
- GAP-CODE: tamanho/TTL do código. Código hoje: 4 dígitos, sem expirar. PR user #8 (6 dígitos + 15 min) pendente. **Não documentar 4 dígitos como regra.**
- GAP-PWD: Encryptors com chave no git. Rotacionar quebra senhas gravadas. Precisa plano (env + rehash) antes de patch.
- GAP-PWD-JSON: retrieve devolve `password`.
- GAP-SUP: `/user/v1/sup` existe interno. Produto quer suporte por HTTP?
- GAP-RECAPTCHA: campos no properties; fluxo de EV no user não usa.
