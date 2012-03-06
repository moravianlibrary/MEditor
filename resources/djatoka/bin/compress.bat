@echo off
if "%OS%" == "Windows_NT" setlocal
rem ---------------------------------------------------------------------------
rem Compress Script for the djatoka compression application
rem
rem Environment Variable Prequisites
rem
rem   JAVA_HOME       Required: Must point at your Java Development Kit installation.
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
rem
rem ---------------------------------------------------------------------------

if "%JAVA_HOME%" == "" goto javahomeerror
if "%DJATOKA_HOME%" == "" goto djatokahomeerror
if "%KAKADU_HOME%" == "" goto kakaduhomeerror   

:gotHome
if exist "%DJATOKA_HOME%\bin\extract.bat" goto okHome
goto djatokahomeerror

:okHome
rem Get standard djatoka environment variables
set JAVA_OPTS=-Djava.awt.headless=true  -Xmx512M -Xms64M -Dkakadu.home="%KAKADU_HOME%" -Djava.library.path="%KAKADU_HOME%"
set CLASSPATH=.;%DJATOKA_HOME%\lib\adore-djatoka-1.1.jar;%DJATOKA_HOME%\lib\commons-cli-1.1.jar;%DJATOKA_HOME%\lib\ij.jar;%DJATOKA_HOME%\lib\jai_codec.jar;%DJATOKA_HOME%\lib\jai_core.jar;%DJATOKA_HOME%\lib\kdu_jni.jar;%DJATOKA_HOME%\lib\log4j-1.2.8.jar;%DJATOKA_HOME%\lib\mlibwrapper_jai.jar;%DJATOKA_HOME%\lib\oom.jar;%DJATOKA_HOME%\lib\oomRef.jar;%DJATOKA_HOME%\lib\uk.co.mmscomputing.imageio.tiff.jar;%DJATOKA_HOME%\lib\ij-ImageIO.jar
rem ----- Execute Command ---------------------------------------
"%JAVA_HOME%\bin\java.exe" %JAVA_OPTS% -classpath "%CLASSPATH%" gov.lanl.adore.djatoka.DjatokaCompress %*
goto end

:javahomeerror
echo JAVA_HOME is not defined.
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