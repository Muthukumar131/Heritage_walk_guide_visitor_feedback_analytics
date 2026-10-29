Write-Host "==============================================================" -ForegroundColor Cyan
Write-Host "Starting Heritage Walk Guide Backend (Spring Boot 3 + Java 21)" -ForegroundColor Cyan
Write-Host "==============================================================" -ForegroundColor Cyan

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$jdkPath = "C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"

[Environment]::SetEnvironmentVariable('JAVA_HOME', $jdkPath, 'Process')
$env:PATH = "$jdkPath\bin;$scriptDir\maven\bin;$env:PATH"

Write-Host "[OK] Using Java 21 from $jdkPath" -ForegroundColor Green
$mvnCmd = Join-Path $scriptDir "maven\bin\mvn.cmd"

Set-Location $scriptDir
& $mvnCmd spring-boot:run
