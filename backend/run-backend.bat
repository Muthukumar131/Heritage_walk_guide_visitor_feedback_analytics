@echo off
setlocal
echo ==============================================================
echo Starting Heritage Walk Guide Backend (Spring Boot 3 + Java 21)
echo ==============================================================

set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
set "PATH=%JAVA_HOME%\bin;%~dp0maven\bin;%PATH%"

echo [OK] Using Java 21: %JAVA_HOME%
echo [OK] Using Maven: %~dp0maven\bin\mvn.cmd

cd /d "%~dp0"
call "%~dp0maven\bin\mvn.cmd" spring-boot:run
pause
