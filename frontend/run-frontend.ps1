Write-Host "==============================================================" -ForegroundColor Cyan
Write-Host "Starting Heritage Walk Guide Frontend (Angular 17 + Bootstrap)" -ForegroundColor Cyan
Write-Host "==============================================================" -ForegroundColor Cyan

if (Get-Command ng -ErrorAction SilentlyContinue) {
    Write-Host "[OK] Global Angular CLI detected. Starting server at http://localhost:4200..." -ForegroundColor Green
    ng serve --open
} else {
    Write-Host "[!] 'ng' CLI not found globally. Running via npx..." -ForegroundColor Yellow
    npx -y @angular/cli serve --open
}
