@echo off
if "%OS%" == "Windows_NT" setlocal
rem ---------------------------------------------------------------------------
rem Compress Script for the djatoka Tomcat Initialization Script
rem
rem Environment Variable Prequisites
rem
rem   JAVA_HOME       Required: Must point at your Java Development Kit installation.
rem
rem   CATALINA_HOME   Required: Must point at your Tomcat installation.
rem
rem   DJATOKA_HOME    Required: Must point at your djatoka installation.
rem                   
rem   KAKADU_HOME     Required: Must point at your Kakadu installation. Also, you'll 
rem                   need to add KAKADU_HOME to your system PATH variable.
rem
rem   1. Define DJATOKA_HOME    
rem	 e.g. set DJATOKA_HOME=C:\Documents and Settings\208183\adore-djatoka-1.0
rem   2. Define KAKADU_HOME     
rem	 e.g. set KAKADU_HOME=C:\Documents and Settings\208183\adore-djatoka-1.0\bin\Win32
rem   3. Define System PATH
rem	 e.g. set PATH=%PATH%;%KAKADU_HOME%
rem   4. Define CATALINA_HOME
rem      e.g. set CATALINA_HOME=C:\Documents and Settings\208183\apache-tomcat-5.5.27
rem
rem ---------------------------------------------------------------------------

if "%JAVA_HOME%" == "" goto javahomeerror
if "%CATALINA_HOME%" == "" goto catalinahomeerror
if "%DJATOKA_HOME%" == "" goto djatokahomeerror
if "%KAKADU_HOME%" == "" goto kakaduhomeerror   

:gotHome
if exist "%DJATOKA_HOME%\bin\tomcat.bat" goto okHome
goto djatokahomeerror

:okHome
rem Get standard djatoka environment variables
set JAVA_OPTS=-Djava.awt.headless=true -Xmx512M -Xms64M -Dkakadu.home="%KAKADU_HOME%" -Djava.library.path="%KAKADU_HOME%"
rem ----- Execute Command ---------------------------------------
"%CATALINA_HOME%\bin\catalina.bat" start
goto end

:javahomeerror
echo The JAVA_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
goto end

:catalinahomeerror
echo The CATALINA_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
goto end

:djatokahomeerror
echo The DJATOKA_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
goto end

:kakaduhomeerror
echo The KAKADU_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
goto end

:end