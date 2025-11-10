# 🎵 MiniSpot - Interface Gráfica com JavaFX

Uma aplicação de gerenciamento de música com interface gráfica moderna usando **JavaFX**.

## 📋 Funcionalidades

### 👤 Artistas
- ✅ Listar todos os artistas
- ✅ Adicionar novo artista
- ✅ Editar nome do artista
- ✅ Excluir artista (e seus álbuns)
- ✅ Ver detalhes do artista

### 💿 Álbuns
- ✅ Listar todos os álbuns
- ✅ Adicionar novo álbum com artista
- ✅ Editar nome e ano do álbum
- ✅ Excluir álbum
- ✅ Ver detalhes do álbum (incluindo faixas)

### 🎵 Faixas
- ✅ Listar todas as faixas
- ✅ Adicionar faixa (Normal ou Favorita)
- ✅ Configurar duração (minutos e segundos)
- ✅ Editar nome da faixa
- ✅ Excluir faixa
- ✅ Ver detalhes da faixa

### 📃 Playlists
- ✅ Listar todas as playlists
- ✅ Criar playlist (Pública ou Privada)
- ✅ Editar nome da playlist
- ✅ Excluir playlist
- ✅ Adicionar faixas à playlist
- ✅ Remover faixas da playlist
- ✅ Calcular duração total da playlist

## 🚀 Como Executar

### Pré-requisitos
- Java 25 ou superior
- Maven 3.6+
- JavaFX SDK (será baixado automaticamente pelo Maven)

### Compilar
```bash
cd MiniSpot
mvn clean compile
```

### Executar
```bash
mvn javafx:run
```

Ou execute via IDE (VS Code, IntelliJ, Eclipse):
```bash
mvn package
java -jar target/MiniSpot-1.0-SNAPSHOT.jar
```

## 📁 Estrutura do Projeto

```
src/main/java/com/example/
├── Main.java                      # CLI (interface de linha de comando)
├── ui/
│   ├── MainApplication.java       # Aplicação JavaFX principal
│   └── controller/
│       ├── MainController.java    # Controlador principal
│       ├── ArtistasController.java
│       ├── AlbunsController.java
│       ├── FaixasController.java
│       └── PlaylistsController.java
├── model/
│   ├── Artista.java
│   ├── Album.java
│   ├── Faixa.java
│   ├── FaixaNormal.java
│   ├── FaixaFavorita.java
│   ├── Playlist.java
│   ├── IFavoritavel.java
│   └── IPlaylistManipulavel.java
└── repository/
    ├── ArtistaRepository.java
    ├── AlbumRepository.java
    ├── FaixaRepository.java
    └── PlaylistRepository.java
```

## 🎨 Design

A interface utiliza:
- **Cores Spotify**: Verde (#1db954) como cor principal
- **Layout Responsivo**: Componentes adaptáveis
- **Emojis**: Para melhor visualização
- **Ícones Intuitivos**: Botões com significado visual

## 🛠️ Tecnologias Utilizadas

- **Java 25**
- **JavaFX 21.0.2** - Framework para UI
- **Maven** - Gerenciador de dependências
- **POO** - Programação Orientada a Objetos

## 📝 Notas

- Os dados são carregados em memória (não persistidos em arquivo)
- A aplicação gera dados de exemplo ao iniciar
- Interface intuitiva e fácil de usar
- Validações básicas em formulários

## 👨‍💻 Autor

Projeto desenvolvido por GitHub Copilot 🤖

---

**Divirta-se gerenciando suas músicas! 🎵🎶**
