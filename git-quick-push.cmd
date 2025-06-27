@echo off
REM Script para agregar, commitear y pushear todos los cambios rápidamente

set /p MSG="Mensaje de commit: "

git add .
git commit -m "%MSG%"
git push origin main

pause
