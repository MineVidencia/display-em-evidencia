# DisplayEmEvidencia

Plugin de formatação e gerenciamento de mensagens no servidor, incluindo chats globais, locais, privados e sistema de staff. Ideal para servidores que desejam mais controle e organização na comunicação entre jogadores e equipe.

## 📦 Funcionalidades

- Chat Global, Local e da Staff
- Chat de Equipe por hierarquia
- Mensagens privadas entre jogadores
- Bloqueio de mensagens privadas
- Sistema de mute/unmute
- Comandos com permissões configuráveis

---

## 📜 Comandos

| Comando            | Descrição                                                                 | Permissão                         |
|--------------------|---------------------------------------------------------------------------|----------------------------------|
| `/g <mensagem>`    | Envia uma mensagem no **chat global**                                     | `displayemevidencia.chat`        |
| `/l <mensagem>`    | Envia uma mensagem no **chat local**                                      | `displayemevidencia.chat`        |
| `/staff <msg>`     | Envia uma mensagem no **chat da staff**                                   | `displayemevidencia.staffchat`   |
| `/team <msg>`      | Envia mensagem para **staff da mesma hierarquia**                         | `displayemevidencia.staff`       |
| `/mute <jogador> <tempo> <motivo>` | Silencia um jogador temporariamente de todos os chats      | `displayemevidencia.mute`        |
| `/unmute <jogador>`| Remove o mute de um jogador                                                | `displayemevidencia.mute`        |
| `/pm <jogador> <msg>` ou `/msg` | Envia uma mensagem privada                                    | `displayemevidencia.default`     |
| `/bloquear <jogador>` | Bloqueia mensagens privadas de um jogador específico                   | `displayemevidencia.default`     |
| `/lockpm <motivo>` | Bloqueia todas as mensagens privadas recebidas                            | `displayemevidencia.default`     |

---

## 🔐 Permissões

| Permissão                         | Descrição                                                      | Padrão |
|----------------------------------|----------------------------------------------------------------|--------|
| `displayemevidencia.default`     | Acesso aos comandos básicos (PM, bloquear, etc)                | `true` |
| `displayemevidencia.chat`        | Acesso aos comandos de chat global e local                     | `true` |
| `displayemevidencia.op`          | Acesso total aos comandos administrativos                      | `op`   |
| `displayemevidencia.staffchat`   | Acesso ao chat da staff (`/staff`)                             | `op`   |
| `displayemevidencia.staff`       | Acesso ao comando `/team` e comandos comuns de staff           | `op`   |
| `displayemevidencia.mute`        | Acesso aos comandos `/mute` e `/unmute`                        | `op`   |