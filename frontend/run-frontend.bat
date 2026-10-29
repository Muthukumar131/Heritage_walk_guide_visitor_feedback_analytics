@echo off
echo ==============================================================
echo Starting Heritage Walk Guide Frontend (Angular 17 + Bootstrap)
echo ==============================================================

where ng >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo Starting with global Angular CLI...
    ng serve --open
) else (
    echo Angular CLI not found globally. Running via npx...
    npx -y @angular/cli serve --open
)
pause
