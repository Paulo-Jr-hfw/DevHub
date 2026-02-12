# DevHub App

[![Status](https://img.shields.io/badge/status-concluído-brightgreen)]()

> App Android em Kotlin usando Jetpack Compose, Retrofit e Gson para buscar perfis do GitHub e mostrar informações em tempo real.

---

## 🛠 Tecnologias utilizadas

- **Kotlin** - linguagem principal
- **Jetpack Compose** - UI moderna declarativa
- **Retrofit + Gson** - consumo de API
- **Room Database** - Persistência local para cache e favoritos
- **Hilt (Dagger)** - Injeção de Dependência para um código desacoplado e testável.
- **Coroutines & Flow** - Programação assíncrona e fluxo de dados em tempo real.
- **Arquitetura MVVM + Repository Pattern** - Separação clara de responsabilidades.
- **Android Studio** - IDE

---

## 🚀 Funcionalidades Atuais

- [x] Busca em tempo real: Pesquisa de perfis do GitHub via API oficial.
- [x] Arquitetura Robusta: Implementação de Injeção de Dependência (Hilt)
- [x] Cache Inteligente: Repositório que gerencia dados locais (Room) e remotos.
- [x] Navegação: Sistema de rotas dinâmicas com Navigation Compose.
- [x] UI Dinâmica: Feedbacks de loading, erro e estados vazios com animações.
- [x] Favoritos Offline: Salve perfis localmente para consultar mesmo sem internet.
- [x] Gerenciamento de Favoritos: Adicione ou remova favoritos diretamente pela tela de perfil ou pela lista geral.

---

## 🎨 Layout / Screenshots
<p align="center">
![Tela Inicial](https://github.com/user-attachments/assets/3ba9569f-ee0b-4ddb-9545-a9b8ddfcc727)
![Tela perfil](https://github.com/user-attachments/assets/c0d936a8-9f5f-41ad-8b67-bb67b77c214c)
![Lista Favoritos](https://github.com/user-attachments/assets/81dd2d4f-4e02-4d8c-bfe9-3daea5fa82eb)
</p>

---


## 📝 Histórico de versões

v0.1 - Estrutura inicial, tela de busca e perfil
v0.2 - Implementação do Navigation Compose e múltiplas telas.
v0.3 - Integração com Retrofit e animações Lottie.
v0.3 - Refatoração e melhorias de UI
v0.4 - Adição da camada de persistência com Room.
v0.5 - Refatoração para Repository Pattern e implementação de DI com Hilt.
v0.6 - Implementação completa da Tela de Favoritos, lógica de Toggle (Favoritar/Desfavoritar) e refinamento de UX.

