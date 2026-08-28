<!-- para IA. não é README de humano. -->
# SPEC — user

status: v0.3
sha: `53dcbe4`
data: 2026-08-28

## Como usar
- Este arquivo é a fonte. Código ≠ spec → **bug de código**. Spec errada → Ricardo muda **este** arquivo, depois o código.
- IDs estáveis (`REGRA-` `DADOS-` `CONTRATO-` `NÃO-` `GAP-`). Não apague ID; marque `revogado`.
- "achei bug" → cita REGRA/CONTRATO. Se não existir, é GAP, não patch.
- "não estamos salvando X" → olha DADOS. Campo ausente = não é bug.
- "cadastrar campo X" → conflita se quebra REGRA/NÃO; senão vira GAP e só então código.
- GAP = pergunta aberta. Não trate GAP como regra.

## Papel
MS **interno** (porta `8083`). CRUD de User, verificação de email, chat. Sem auth HTTP (interno). Dono dos dados de pessoa. **Não** é dono de senha (produto sem senha).

## REGRA
- REGRA-USER-1: user persistido tem `id` (UUID), `name`, `email`, `type` (default `client`), `deleted`. Coluna `password` é **legado**: não preencher no cadastro/login, não usar para autenticar.
- REGRA-USER-4: HTTP `/user/v1/create` é **stub** (não persiste). Signup real = CONTRATO-EV-OK. No cadastro, `name` nasce null; `type` = `client`.
- REGRA-USER-5: cadastro **não** pede nome. Nome o usuário atualiza depois (update do próprio user / perfil).
- REGRA-USER-2: email único entre não-deletados.
- REGRA-USER-3: **revogado** (2026-08-28). Produto sem senha. Encryptors no código = legado (GAP-PWD).
- REGRA-AUTH-CODE-1: identidade = email + código de uso único. Sem senha no cadastro e no login.
- REGRA-EV-1: um fluxo só. Pede código → valida código (sem senha) → email **novo** cria User; email **existente** autentica. Os dois emitem token na borda.
- REGRA-EV-2: código vive em `EmailVerification` (`email`, `readableNumber`, `lastEmailSent`).
- REGRA-EV-3: código = 6 dígitos; válido 15 min a partir de `lastEmailSent` (PR user #8).
- REGRA-EMAIL-MOCK: por hora **não** envia SES. `sendValidationEmail` **devolve** `readableNumber` no JSON. Front abre a tela de código com os campos já populados. Quando GAP-EMAIL-REAL fechar, parar de devolver o código.
- REGRA-DEL-1: delete é soft (`deleted=true`) salvo spec futura.
- REGRA-CHAT-1: conversa pertence a `userId`. Mensagens salvam texto + metadados.
- REGRA-GURU-USER: aluno é **global**. Um `userId` estuda com vários gurus. User **não** tem `guruId`.

## NÃO
- NÃO-EXPOSE: não é API pública. Quem chama da internet é o firewall.
- NÃO-JWT
- NÃO-SHUTDOWN
- NÃO-FACTDADOS: tabela `FactDados` **não** faz parte do produto Digitus Forum (legado). Não evoluir.
- NÃO-PASSWORD: não pedir, não persistir, não validar senha em cadastro nem login.
- NÃO-RESET: `sendResetPasswordEmail` / `resetPassword` **fora** do produto enquanto NÃO-PASSWORD.
- NÃO-CAPTCHA: recaptcha **não** entra enquanto REGRA-EMAIL-MOCK. Liga junto com GAP-EMAIL-REAL.

## DADOS
| id | tabela | campos |
|---|---|---|
| DADOS-USER | User | id, name, email, type, deleted. `password` coluna legado, não usar. |
| DADOS-EV | EmailVerification | emailVerificationId, email, readableNumber, lastEmailSent |
| DADOS-SUBJ | ChatSubject | chatSubjectId, userId, name, privateOrPublic, status, lastUpdated, deleted |
| DADOS-MSG | ChatMessage | chatMessageId, chatSubjectId, userId, userName, userEmail, userType, message, status, position. `alignment` é calculado na leitura, **não** persiste. |

Não salva ainda: recaptcha, token (token é do firewall), perfil, curso, matrícula, guruId. Ver GAP-COMPRA (course). Aluno global: sem guru neste MS.

## CONTRATO
User: `/user/v1/create` (**stub, não salva**) · `/{id}/retrieve` · `/{id}/update` (nome etc.) · `/{id}/delete`
**Revogados como produto:** `/create/validateEmail` (nome mentiroso, era login email+senha) · `/retrieve/byEmailAndPassword`
Email: CONTRATO-EV-SEND `/emailVerification/v1/sendValidationEmail` body `{email}` → persiste código; **mock:** response inclui `readableNumber`
CONTRATO-EV-OK `/emailVerification/v1/validateEmail` body `{email, readableNumber}` **sem senha** → se email novo, cria User (`name` null); se existe, não duplica. Borda emite token depois.
**Revogados:** `sendResetPasswordEmail` · `resetPassword`
Chat: `/user/v1/chat` · `conversations` · `conversation` · `sup` (interno; borda `/firewall/sup` **não** existe)
Health: `/user/v1/healthCheck`

`/user/v1/retrieve` (lista) está comentado — **não** está na spec.

## GAP
- GAP-CODE: **revogado** (2026-08-28). REGRA-EV-3 (6 dígitos, 15 min).
- GAP-PWD: Encryptors com chave no git. Legado; não é produto. Não rotacionar como se senha existisse.
- GAP-PWD-JSON: retrieve ainda pode devolver coluna `password`. Código deve parar de devolver (não é auth).
- GAP-SUP: `/user/v1/sup` existe interno. Produto quer suporte por HTTP?
- GAP-RECAPTCHA: **revogado** como regra agora. Recaptcha só em GAP-EMAIL-REAL.
- GAP-EMAIL-REAL: ligar SES de verdade; **parar** de devolver `readableNumber` no JSON; ligar recaptcha no send. Front deixa de pré-preencher: usuário digita o código do email.
