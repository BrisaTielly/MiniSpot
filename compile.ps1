# Script de Compilação e Execução do MiniSpot com JavaFX
# PowerShell Script para Windows

# Cores para output
$Green = "`e[92m"
$Red = "`e[91m"
$Yellow = "`e[93m"
$Blue = "`e[94m"
$Reset = "`e[0m"

Write-Host "$Blue╔══════════════════════════════════════════════════════╗$Reset"
Write-Host "$Blue║         🎵 MiniSpot - Compilador JavaFX 🎵          ║$Reset"
Write-Host "$Blue╚══════════════════════════════════════════════════════╝$Reset"

# Configurações
$JAVAFX_SDK = "C:\javafx-sdk-21"  # ALTERE AQUI SE SEU JAVAFX ESTÁ EM OUTRO LOCAL
$SRC_DIR = "src\main\java"
$TARGET_DIR = "target\classes"
$RESOURCES_DIR = "src\main\resources"

# Verificar se JavaFX SDK existe
Write-Host "$Yellow[*] Verificando JavaFX SDK em: $JAVAFX_SDK$Reset"
if (-not (Test-Path $JAVAFX_SDK)) {
    Write-Host "$Red[❌] JavaFX SDK não encontrado em: $JAVAFX_SDK$Reset"
    Write-Host "$Yellow[!] Baixe em: https://gluonhq.com/products/javafx/$Reset"
    exit 1
}
Write-Host "$Green[✅] JavaFX SDK encontrado!$Reset"

# Verificar Java
Write-Host "$Yellow[*] Verificando Java...$Reset"
$java_version = & java -version 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "$Red[❌] Java não encontrado ou não está no PATH$Reset"
    exit 1
}
Write-Host "$Green[✅] Java encontrado:$Reset"
Write-Host "$java_version"

# Criar diretórios
Write-Host "$Yellow[*] Criando diretórios...$Reset"
if (-not (Test-Path $TARGET_DIR)) {
    New-Item -ItemType Directory -Force -Path $TARGET_DIR | Out-Null
    Write-Host "$Green[✅] Diretório criado: $TARGET_DIR$Reset"
}

# Copiar recursos (CSS, etc)
Write-Host "$Yellow[*] Copiando recursos...$Reset"
if (Test-Path $RESOURCES_DIR) {
    Copy-Item "$RESOURCES_DIR\*" "$TARGET_DIR\" -Recurse -Force
    Write-Host "$Green[✅] Recursos copiados!$Reset"
}

# Compilar
Write-Host "$Yellow[*] Compilando classes Java...$Reset"
$source_files = Get-ChildItem -Path $SRC_DIR -Recurse -Filter "*.java"
$source_count = $source_files.Count

if ($source_count -eq 0) {
    Write-Host "$Red[❌] Nenhum arquivo Java encontrado em $SRC_DIR$Reset"
    exit 1
}

Write-Host "$Blue[📝] Encontrados $source_count arquivos Java$Reset"

$compilation_args = @(
    "--module-path", "$JAVAFX_SDK\lib",
    "--add-modules", "javafx.controls,javafx.fxml",
    "-d", $TARGET_DIR,
    "-sourcepath", $SRC_DIR
)

# Adicionar todos os arquivos source
$compilation_args += $source_files.FullName

Write-Host "$Blue[⚙️ ] Compilando com javac...$Reset"
& javac.exe $compilation_args

if ($LASTEXITCODE -eq 0) {
    Write-Host "$Green[✅] Compilação concluída com sucesso!$Reset"
    Write-Host ""
    Write-Host "$Green╔══════════════════════════════════════════════════════╗$Reset"
    Write-Host "$Green║            🎉 Compilação Concluída! 🎉              ║$Reset"
    Write-Host "$Green╚══════════════════════════════════════════════════════╝$Reset"
    Write-Host ""
    Write-Host "$Yellow[📌] Para executar a aplicação, use:$Reset"
    Write-Host ""
    Write-Host "$Blue`$JAVAFX_SDK = `"$JAVAFX_SDK`"$Reset"
    Write-Host "$Blue java --module-path `"`$JAVAFX_SDK\lib`" \$Reset"
    Write-Host "$Blue      --add-modules javafx.controls,javafx.fxml \$Reset"
    Write-Host "$Blue      -cp $TARGET_DIR \$Reset"
    Write-Host "$Blue      com.example.ui.MainApplication$Reset"
    Write-Host ""
    Write-Host "$Yellow[🚀] Ou execute o script de execução: run.ps1$Reset"
} else {
    Write-Host "$Red[❌] Erro durante a compilação!$Reset"
    exit 1
}
