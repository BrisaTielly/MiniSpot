# � MiniSpot - Spotify Minimalista

Um projeto de gerenciamento de música com **interface gráfica JavaFX** moderna e responsiva!

## ✨ O Que Você Consegue Fazer

### 👤 Gerenciar Artistas
- ✅ Adicionar, editar e excluir artistas
- ✅ Ver detalhes e lista de álbuns
- ✅ Interface intuitiva com avatares

### 💿 Gerenciar Álbuns  
- ✅ Criar álbuns associados a artistas
- ✅ Editar nome e ano de lançamento
- ✅ Visualizar todas as faixas do álbum
- ✅ Seleção visual de artista via ComboBox

### 🎵 Gerenciar Faixas
- ✅ Adicionar faixas normais ou favoritas (⭐)
- ✅ Configurar duração em minutos e segundos
- ✅ Editar e excluir faixas
- ✅ Filtro visual por tipo (normal/favorita)

### 📃 Gerenciar Playlists
- ✅ Criar playlists públicas (🌍) ou privadas (🔒)
- ✅ Adicionar/remover faixas dinamicamente
- ✅ Calcular duração total automática (⏱️)
- ✅ Interface com abas (playlists + faixas)

---

## 🏗️ Arquitetura

```
MiniSpot/
├── src/main/java/com/example/
│   ├── Main.java                    # CLI (linha de comando)
│   ├── ui/
│   │   ├── MainApplication.java     # App JavaFX principal
│   │   └── controller/
│   │       ├── MainController.java
│   │       ├── ArtistasController.java
│   │       ├── AlbunsController.java
│   │       ├── FaixasController.java
│   │       └── PlaylistsController.java
│   ├── model/
│   │   ├── Artista.java
│   │   ├── Album.java
│   │   ├── Faixa.java (abstrata)
│   │   ├── FaixaNormal.java
│   │   ├── FaixaFavorita.java
│   │   ├── Playlist.java
│   │   ├── IFavoritavel.java
│   │   └── IPlaylistManipulavel.java
│   └── repository/
│       ├── ArtistaRepository.java
│       ├── AlbumRepository.java
│       ├── FaixaRepository.java
│       └── PlaylistRepository.java
├── src/main/resources/
│   └── styles.css
├── pom.xml
├── compile.ps1                      # Script PowerShell compilação
├── run.ps1                          # Script PowerShell execução
└── INSTALACAO.md                    # Guia detalhado
```

---

## 🚀 Quick Start

### Opção 1: Com Maven (Fácil ⭐)
```powershell
mvn clean compile
mvn javafx:run
```

### Opção 2: PowerShell Scripts
```powershell
# Compilar
.\compile.ps1

# Executar
.\run.ps1
```

### Opção 3: Manual com javac
```powershell
$JAVAFX_SDK = "C:\javafx-sdk-21"

javac --module-path "$JAVAFX_SDK\lib" \
      --add-modules javafx.controls,javafx.fxml \
      -d target/classes \
      $(Get-ChildItem src/main/java -Recurse -Filter "*.java").FullName

java --module-path "$JAVAFX_SDK\lib" \
     --add-modules javafx.controls,javafx.fxml \
     -cp target/classes \
     com.example.ui.MainApplication
```

👉 **Veja [INSTALACAO.md](INSTALACAO.md) para instruções detalhadas!**

---

## 📦 Requisitos

- ✅ Java 11+ 
- ✅ JavaFX 21.0.2 (baixa automático com Maven)
- ✅ Maven 3.6+ (opcional)

---

## 🎨 Interface

### Design Spotify-Inspired
- **Cores**: Verde Spotify (#1db954) + Preto (#191414)
- **Componentes**: Botões, listas, abas, spinners
- **Responsividade**: Redimensionável e adaptável
- **Emojis**: Ícones intuitivos em português

### Telas Principais

**Artistas**
```
┌─────────────────────────────────────┐
│ 👤 Artistas                         │
├─────────────────────────────────────┤
│ Nome: [__________] ➕ Adicionar     │
├─────────────────────────────────────┤
│ • 👤 Fernanda Paula  [✏️] [🗑️] [👁️]│
│ • 👤 João Silva      [✏️] [🗑️] [👁️]│
│ • 👤 Maria Santos    [✏️] [🗑️] [👁️]│
└─────────────────────────────────────┘
```

**Playlists** (view dupla)
```
┌────────────────────┬────────────────────┐
│ � Playlists       │ 🎵 Faixas         │
├────────────────────┼────────────────────┤
│ • 🌍 Favoritas     │ ⭐ Noite Estrelada│
│ • 🔒 Relaxar       │ ⭐ Melodia...     │
│ • 🌍 Rock          │ ♪ Vento do Sul   │
├────────────────────┼────────────────────┤
│ [✏️] [🗑️]         │ [➕] [🗑️]        │
└────────────────────┴────────────────────┘
```

---

## 🎓 Conceitos OOP Aplicados

| Conceito | Implementação |
|----------|---------------|
| **Abstração** | `Faixa` é classe abstrata |
| **Herança** | `FaixaNormal` e `FaixaFavorita` herdam de `Faixa` |
| **Polimorfismo** | Métodos `tocar()` sobrescritos |
| **Encapsulamento** | Atributos privados com getters/setters |
| **Interface** | `IFavoritavel`, `IPlaylistManipulavel` |
| **Composição** | Artista → Álbun → Faixa |
| **Pattern Repository** | Repositories para persistência |

---

## 📝 Dados de Exemplo

A aplicação carrega automaticamente:
- **3 Artistas**: Fernanda Paula, João Silva, Maria Santos
- **2 Álbuns**: "Amanhecer", "Horizontes"  
- **4 Faixas**: Mix de normais e favoritas
- **1 Playlist**: "Minhas Favoritas"

---

## 🐛 Troubleshooting

| Problema | Solução |
|----------|---------|
| Maven não encontrado | Instale Maven ou use PowerShell scripts |
| JavaFX não compilou | Baixe JavaFX SDK em https://gluonhq.com/products/javafx/ |
| "module not found" | Verifique caminho do JAVAFX_SDK |
| App não abre | Confirme Java 11+, recompile |

---

## � Recursos

- [JavaFX Docs](https://openjfx.io/)
- [Maven Guide](https://maven.apache.org/)  
- [Java Modules](https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/lang/module/package-summary.html)

---

## 🏗️ Estrutura de Classes (POO)

### 1. Entidades Principais

#### Artista
- **Atributos:** id, nome, listaAlbuns
- **Métodos:** exibirAlbuns(), exibirTopFaixas()
- **CRUD:** criar, listar, atualizar nome, excluir
- **POO:** Composição (contém lista de álbuns)

#### Album
- **Atributos:** id, nome, artista, ano, listaFaixas
- **Métodos:** exibirFaixas()
- **CRUD:** criar, listar, atualizar, excluir
- **POO:** Composição (contém lista de faixas); associação com Artista

#### Faixa (Classe Abstrata)
- **Atributos:** id, nome, duração
- **Métodos abstratos:** tocar(), exibirInfo()
- **POO:** Classe abstrata (não é instanciada diretamente)
- **Herança/Polimorfismo:** criar subclasses `FaixaNormal` e `FaixaFavorita` que implementam métodos abstratos de formas diferentes

#### Playlist
- **Atributos:** id, nome, listaFaixas, pública/privada
- **Métodos:** adicionarFaixa(), removerFaixa(), mostrarPlaylist()
- **POO:** Composição (contém lista de Faixas)
- **Interface:** Implementar `IPlaylistManipulavel` para manipulação de faixas

### 2. Interfaces
- `IFavoritavel` → método `favoritar()`, implementado por `FaixaFavorita`
- `IPlaylistManipulavel` → métodos `adicionarFaixa()`, `removerFaixa()`, `reordenar()`, implementado por Playlist

---

## ✅ Funcionalidades Implementadas

### CRUD e Operações
- ✅ CRUD de artistas
- ✅ CRUD de álbuns
- ✅ CRUD de faixas (normal e favorita)
- ✅ CRUD de playlists
- ✅ Adicionar/remover faixas em playlists
- ✅ Interface gráfica JavaFX completa
- ✅ Validações de entrada
- ⏳ Persistência em arquivo (próxima versão)

---

**🎵 Divirta-se com o MiniSpot! 🎵**