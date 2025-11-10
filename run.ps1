# Script de Execução do MiniSpot
# PowerShell Script para Windows

# Cores
$Green = "`e[92m"
$Red = "`e[91m"
$Yellow = "`e[93m"
$Blue = "`e[94m"
$Reset = "`e[0m"

Write-Host "$Blue╔══════════════════════════════════════════════════════╗$Reset"
Write-Host "$Blue║             🎵 MiniSpot - Executando 🎵             ║$Reset"
Write-Host "$Blue╚══════════════════════════════════════════════════════╝$Reset"

# Configurações
$JAVAFX_SDK = "C:\javafx-sdk-21"
$TARGET_DIR = "target\classes"

# Verificar se os arquivos compilados existem
if (-not (Test-Path "$TARGET_DIR\com\example\ui\MainApplication.class")) {
    Write-Host "$Red[❌] Classe não encontrada. Compile primeiro com: .\compile.ps1$Reset"
    exit 1
}

Write-Host "$Green[✅] Arquivos compilados encontrados!$Reset"
Write-Host ""
Write-Host "$Yellow[🚀] Iniciando MiniSpot...$Reset"
Write-Host ""

# Executar
$execution_args = @(
    "--module-path", "$JAVAFX_SDK\lib",
    "--add-modules", "javafx.controls,javafx.fxml",
    "-cp", $TARGET_DIR,
    "com.example.ui.MainApplication"
)

& java.exe $execution_args

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "$Red[❌] Erro ao executar a aplicação!$Reset"
    Write-Host "$Yellow[!] Verifique se o JavaFX SDK está em: $JAVAFX_SDK$Reset"
}
