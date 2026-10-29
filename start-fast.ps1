Write-Host "==============================================================" -ForegroundColor Cyan
Write-Host "  HERITAGE WALK GUIDE - HIGH SPEED UNIFIED LAUNCHER" -ForegroundColor Cyan
Write-Host "==============================================================" -ForegroundColor Cyan

$rootDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$jdkPath = "C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"

[Environment]::SetEnvironmentVariable('JAVA_HOME', $jdkPath, 'Process')
$env:PATH = "$jdkPath\bin;$rootDir\backend\maven\bin;$env:PATH"
$env:MAVEN_OPTS = "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

Write-Host "[*] Launching browser to http://localhost:8080 in 3 seconds..." -ForegroundColor Yellow
Start-Job -ScriptBlock { Start-Sleep -Seconds 3; Start-Process 'http://localhost:8080' } | Out-Null

Write-Host "[*] Starting unified server (Frontend + Backend + DB on port 8080)..." -ForegroundColor Green
Set-Location "$rootDir\backend"
$mvnCmd = Join-Path $rootDir "backend\maven\bin\mvn.cmd"
& $mvnCmd spring-boot:run
