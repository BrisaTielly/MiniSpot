# 🎵 MiniSpot - Guia de Instalação e Execução

## ⚙️ Pré-requisitos

### 1. Java Development Kit (JDK)
Você precisa de **Java 11 ou superior** instalado.

**Verificar versão atual:**
```powershell
java -version
javac -version
```

Se não tiver Java instalado, baixe em: https://www.oracle.com/java/technologies/downloads/

### 2. Maven (opcional, recomendado)
Para facilitar a compilação e execução.

**Verificar Maven:**
```powershell
mvn -version
```

Se não tiver Maven:
1. Baixe em: https://maven.apache.org/download.cgi
2. Extraia em um diretório (ex: `C:\maven`)
3. Adicione ao PATH do Windows:
   - Variável: `M2_HOME` → `C:\maven`
   - PATH: `%M2_HOME%\bin`

## 🚀 Opção 1: Compilar com Maven (Recomendado)

```powershell
# 1. Abra o terminal na pasta do projeto
cd "c:\Users\jv806\OneDrive\Área de Trabalho\Github\MiniSpot"

# 2. Limpe e compile
mvn clean compile

# 3. Empacote
mvn package

# 4. Execute
mvn javafx:run
```

**Ou execute o JAR gerado:**
```powershell
java -jar target/MiniSpot.jar
```

## 🚀 Opção 2: Compilar com javac (sem Maven)

### Passo 1: Baixar JavaFX SDK

1. Acesse: https://gluonhq.com/products/javafx/
2. Baixe a versão **21.0.2** para Windows
3. Extraia em um diretório (ex: `C:\javafx-sdk-21`)

### Passo 2: Compilar

**Opção A - Windows (executar compile.bat):**
```powershell
# Edite o arquivo compile.bat e altere:
set JAVAFX_SDK=C:\javafx-sdk-21  # Caminho do seu JavaFX

# Execute:
.\compile.bat
```

**Opção B - Manual com PowerShell:**
```powershell
$JAVA_HOME = "C:\Program Files\Java\jdk-11"  # Seu caminho Java
$JAVAFX_SDK = "C:\javafx-sdk-21"
$SRC_DIR = "src\main\java"
$TARGET_DIR = "target\classes"

# Criar diretório de saída
New-Item -ItemType Directory -Force -Path $TARGET_DIR | Out-Null

# Compilar
& "$JAVA_HOME\bin\javac.exe" `
  --module-path "$JAVAFX_SDK\lib" `
  --add-modules javafx.controls,javafx.fxml `
  -d $TARGET_DIR `
  -sourcepath $SRC_DIR `
  (Get-ChildItem -Path $SRC_DIR -Recurse -Filter "*.java").FullName

Write-Host "✅ Compilação completa!"
```

### Passo 3: Executar

```powershell
$JAVAFX_SDK = "C:\javafx-sdk-21"
$TARGET_DIR = "target\classes"

java --module-path "$JAVAFX_SDK\lib" `
     --add-modules javafx.controls,javafx.fxml `
     -cp $TARGET_DIR `
     com.example.ui.MainApplication
```

## 🖥️ Opção 3: Executar pela IDE

### VS Code
1. Abra a pasta do projeto
2. Instale a extensão "Extension Pack for Java"
3. Clique com botão direito em `MainApplication.java`
4. Selecione "Run" ou "Debug"

### IntelliJ IDEA
1. Abra o projeto
2. Clique com botão direito em `MainApplication.java`
3. Selecione "Run 'MainApplication.main()'"

### Eclipse
1. Importe como "Maven Project"
2. Clique com botão direito → "Run As" → "Maven build"
3. Goals: `clean javafx:run`

## ✅ Interface Gráfica

Quando a aplicação inicia, você verá:

### 🎨 Layout Principal
- **Header**: Título e descrição do app
- **Abas**: 4 abas principais
  - 👤 **Artistas**: Gerenciar artistas
  - 💿 **Álbuns**: Gerenciar álbuns
  - 🎵 **Faixas**: Gerenciar faixas
  - 📃 **Playlists**: Gerenciar playlists

### 🎮 Funcionalidades
- ➕ Adicionar itens
- ✏️ Editar itens
- 🗑️ Excluir itens
- 👁️ Ver detalhes

### 📊 Dados Iniciais
A aplicação carrega automaticamente:
- 3 artistas de exemplo
- 2 álbuns de exemplo
- 4 faixas de exemplo
- 1 playlist de exemplo

## 🐛 Solução de Problemas

### Erro: "javafx-controls not found"
- **Causa**: Maven não conseguiu baixar dependências
- **Solução**: 
  ```powershell
  mvn dependency:resolve
  ```

### Erro: "module not found: javafx"
- **Causa**: Caminho do JavaFX incorreto
- **Solução**: Verifique o caminho em `JAVAFX_SDK`

### Erro: "Cannot find symbol: class Application"
- **Causa**: JavaFX não está no classpath
- **Solução**: Adicione `--module-path` e `--add-modules` ao compilar

### Aplicação não abre
- Verifique se Java é versão 11+
- Reinstale o JavaFX SDK
- Tente recompile com `mvn clean compile`

## 📚 Referências

- [JavaFX Documentation](https://openjfx.io/)
- [Maven Guide](https://maven.apache.org/guides/)
- [Java Modules](https://openjfx.io/openjfx-docs/)

---

**Pronto! Agora é só aproveitar o MiniSpot! 🎵🎶**
