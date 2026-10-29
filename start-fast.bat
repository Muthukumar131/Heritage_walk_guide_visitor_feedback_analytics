@echo off
setlocal
title Heritage Walk Guide - Fast Production Runner
echo ==============================================================
echo   HERITAGE WALK GUIDE - HIGH SPEED UNIFIED LAUNCHER
echo ==============================================================

set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
set "PATH=%JAVA_HOME%\bin;%~dp0backend\maven\bin;%PATH%"
set "MAVEN_OPTS=-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

echo [*] Starting Spring Boot (serving both REST API and pre-compiled Angular)...
cd /d "%~dp0backend"

:: Launch browser in background after 3 seconds
start "" powershell -NoProfile -Command "Start-Sleep -Seconds 3; Start-Process 'http://localhost:8080'"

:: Start Spring Boot
call "%~dp0backend\maven\bin\mvn.cmd" spring-boot:run
pause
