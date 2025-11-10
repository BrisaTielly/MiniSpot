@echo off
REM Script de compilação do MiniSpot com JavaFX

setlocal enabledelayedexpansion

REM Diretórios
set JAVA_HOME=%JAVA_HOME%
set SRC_DIR=src\main\java
set RESOURCES_DIR=src\main\resources
set TARGET_DIR=target\classes
set JAVAFX_SDK=C:\javafx-sdk-21

REM Criar diretórios
if not exist %TARGET_DIR% mkdir %TARGET_DIR%

REM Compilar classes Java
echo Compilando classes Java...
dir /s /B %SRC_DIR%\*.java > sources.txt

REM Usar javac com JavaFX
echo Compilando com JavaFX...
javac --module-path %JAVAFX_SDK%\lib --add-modules javafx.controls,javafx.fxml -d %TARGET_DIR% @sources.txt

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Compilação concluída com sucesso!
    echo.
    echo Para executar:
    echo java --module-path %JAVAFX_SDK%\lib --add-modules javafx.controls,javafx.fxml -cp %TARGET_DIR% com.example.ui.MainApplication
) else (
    echo ❌ Erro na compilação!
)

del sources.txt
pause
