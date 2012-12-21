Jars in this directory are, unfortunately, not hosted in any Maven repository

Use these commands to install them manually:
--------------------------------------------

mvn install:install-file -DgroupId=com.foxtrottechnologies -DartifactId=javadjvu -Dversion=0.8.09 -Dpackaging=jar -Dfile=./javadjvu-0.8.09.jar
mvn install:install-file -DgroupId=mzk.marc4j -DartifactId=marc4j -Dversion=2.3.1 -Dpackaging=jar -Dfile=./marc4j.jar
mvn install:install-file -DgroupId=mzk.ki -DartifactId=a2jruntime -Dversion=1.1.1 -Dpackaging=jar -Dfile=./a2jruntime.jar
mvn install:install-file -DgroupId=ki -DartifactId=ki-util -Dversion=1.0 -Dpackaging=jar -Dfile=./ki-util.jar
mvn install:install-file -DgroupId=mzk.ki -DartifactId=ki-jzkit-iface -Dversion=1.2.3 -Dpackaging=jar -Dfile=./ki-jzkit-iface-1_2_3.jar
mvn install:install-file -DgroupId=mzk.ki -DartifactId=ki-jzkit-z3950 -Dversion=1.2.3 -Dpackaging=jar -Dfile=./ki-jzkit-z3950-1_2_3.jar
