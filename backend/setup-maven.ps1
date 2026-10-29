$ErrorActionPreference = 'Stop'
$dest = 'C:\Users\rajkumar\.gemini\antigravity\scratch\heritage-walk-guide\backend\maven'
if (!(Test-Path $dest)) {
    New-Item -ItemType Directory -Path $dest -Force | Out-Null
}

$zip = Join-Path $env:TEMP 'apache-maven-bin.zip'
Write-Host "Downloading Apache Maven 3.9.6 (~9MB)..."
Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip' -OutFile $zip

Write-Host "Extracting Maven..."
$extractDir = Join-Path $env:TEMP 'mvn-extract'
Expand-Archive -Path $zip -DestinationPath $extractDir -Force

Copy-Item -Path "$extractDir\apache-maven-3.9.6\*" -Destination $dest -Recurse -Force
Write-Host "Maven installed at: $dest"

$mvnCmd = Join-Path $dest 'bin\mvn.cmd'
& $mvnCmd -version
