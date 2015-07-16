@echo off

cd /d %~dp0

call mvn clean install -U -Dmaven.test.skip=true
if %errorlevel% == 0 (echo. & echo [Building] Successful. & echo. & goto :eof)
if %errorlevel% == 1 (echo. & echo [Building] Failed. & echo. & pause)