# 🚀 COMO EXECUTAR O MINISPOT - GUIA PRÁTICO

## ❌ Problema Identificado
Maven e JavaFX SDK não estão instalados no seu sistema.

## ✅ Solução: Instalar o Necessário

### 1️⃣ INSTALAR JAVAFX SDK (5 minutos)

**Passo 1: Baixar**
1. Acesse: https://gluonhq.com/products/javafx/
2. Selecione: Windows
3. Versão: 21.0.2 ou superior
4. Clique: Download

**Passo 2: Extrair**
1. Extraia o arquivo em: `C:\javafx-sdk-21`
2. Você deve ter: `C:\javafx-sdk-21\lib\javafx-*.jar`

**Passo 3: Verificar**
```powershell
Test-Path "C:\javafx-sdk-21\lib"
```

### 2️⃣ INSTALAR MAVEN (5 minutos - OPCIONAL)

Se quiser usar Maven:

**Passo 1: Baixar**
1. Acesse: https://maven.apache.org/download.cgi
2. Baixe: apache-maven-3.9.x-bin.zip

**Passo 2: Extrair**
1. Extraia em: `C:\maven`

**Passo 3: Configurar PATH**
1. Abra: Variáveis de Ambiente do Windows
2. Clique: Variáveis de Ambiente
3. Adicione ao PATH: `C:\maven\bin`
4. Reinicie PowerShell

**Passo 4: Verificar**
```powershell
mvn --version
```

---

## 🎯 EXECUTAR O PROJETO

### OPÇÃO 1: Com Maven (Depois de instalar)

```powershell
cd "c:\Users\jv806\OneDrive\Área de Trabalho\Github\MiniSpot"
mvn clean compile
mvn javafx:run
```

### OPÇÃO 2: Com PowerShell Script (Depende de JavaFX SDK)

**1. Edite o script `compile.ps1`:**
- Abra: `compile.ps1`
- Encontre: `$JAVAFX_SDK = "C:\javafx-sdk-21"`
- Altere para o seu caminho (ex: `C:\javafx-sdk-21`)

**2. Execute:**
```powershell
cd "c:\Users\jv806\OneDrive\Área de Trabalho\Github\MiniSpot"
.\compile.ps1
.\run.ps1
```

### OPÇÃO 3: Com VS Code (Recomendado - Mais Fácil!)

**1. Instale Extensões:**
   - "Extension Pack for Java" (Microsoft)
   - "JavaFX Support" (opcional)

**2. Abra o Projeto:**
   - File → Open Folder
   - Selecione: MiniSpot

**3. Configure o Java:**
   - Pressione: Ctrl+Shift+P
   - Digite: "Java: Configure Java Runtime"
   - Selecione: JDK 11 ou superior

**4. Execute:**
   - Abra: `src/main/java/com/example/ui/MainApplication.java`
   - Clique: Run (botão acima da classe)
   - OU Pressione: Ctrl+F5

### OPÇÃO 4: Com IntelliJ IDEA (Mais Fácil Ainda!)

**1. Abra o Projeto:**
   - File → Open
   - Selecione pasta: MiniSpot

**2. Configure Dependências:**
   - Right-click em `pom.xml`
   - "Add as Maven Project"

**3. Execute:**
   - Right-click em `MainApplication.java`
   - "Run 'MainApplication.main()'"

### OPÇÃO 5: Compilação Manual com javac

```powershell
$JAVAFX_SDK = "C:\javafx-sdk-21"
$SRC_DIR = "src\main\java"
$TARGET_DIR = "target\classes"

# Criar diretório
New-Item -ItemType Directory -Force -Path $TARGET_DIR | Out-Null

# Compilar
javac --module-path "$JAVAFX_SDK\lib" `
      --add-modules javafx.controls,javafx.fxml `
      -d $TARGET_DIR `
      -sourcepath $SRC_DIR `
      $(Get-ChildItem -Path $SRC_DIR -Recurse -Filter "*.java" | % FullName)

# Executar
java --module-path "$JAVAFX_SDK\lib" `
     --add-modules javafx.controls,javafx.fxml `
     -cp $TARGET_DIR `
     com.example.ui.MainApplication
```

---

## ⚡ RECOMENDAÇÃO RÁPIDA

**Caminho Mais Rápido (< 10 minutos):**

1. Instale VS Code (se não tiver): https://code.visualstudio.com/
2. Instale Extension Pack for Java
3. Abra o projeto em VS Code
4. Clique Run no MainApplication.java

**VS Code vai:**
- ✅ Baixar Java automaticamente
- ✅ Configurar dependências Maven
- ✅ Executar a aplicação

---

## 🐛 SE AINDA TIVER PROBLEMAS

### Erro: "Module not found: javafx"
**Causa:** JavaFX SDK não está no caminho correto
**Solução:** 
1. Baixe JavaFX em https://gluonhq.com/products/javafx/
2. Extraia em `C:\javafx-sdk-21`
3. Verifique: `C:\javafx-sdk-21\lib` existe?

### Erro: "Cannot find Java compiler"
**Causa:** Java não está configurado
**Solução:**
1. Verifique: `java -version`
2. Se não aparecer, instale: https://www.oracle.com/java/technologies/downloads/

### Erro: "Port 8080 already in use"
**Causa:** Outra aplicação usando porta
**Solução:** Feche outras abas/aplicações

### Erro em VS Code
**Solução:**
1. Pressione Ctrl+Shift+P
2. Digite: "Java: Clean Language Server Workspace"
3. Selecione: "Yes"
4. Reinicie VS Code

---

## ✅ QUANDO FUNCIONAR

Você verá uma janela com:
```
╔═══════════════════════════════════╗
│ 🎵 MiniSpot                      │
│ Seu Spotify Favorito             │
├───────────────────────────────────┤
│ [👤] [💿] [🎵] [📃]             │
├───────────────────────────────────┤
│ Nome: [_________] ➕ Adicionar   │
│                                   │
│ • Artista 1                       │
│ • Artista 2                       │
│ • Artista 3                       │
└═══════════════════════════════════┘
```

🎉 **Pronto! Divirta-se!**

---

## 📞 SUPORTE RÁPIDO

| Problema | Solução Rápida |
|----------|----------------|
| "Qual é o mais fácil?" | **VS Code + Extension Pack** |
| "Não tenho Maven" | **Use VS Code, ele configura sozinho** |
| "Não tenho JavaFX" | **Baixe em gluonhq.com** |
| "Não entendi nada" | **Use IntelliJ IDEA - é mais visual** |

---

**🎵 Boa sorte! E aproveite o MiniSpot! 🎵**
