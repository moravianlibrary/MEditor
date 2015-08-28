FROM debian:sid

MAINTAINER Martin Rumanek <martin@rumanek.cz>

ENV REFRESHED_AT 2015-07-14

## install packages
RUN apt-get -y  update && apt-get -y install unzip curl libtiff-tools imagemagick sox ffmpeg libsox-fmt-mp3 

# run tomcat
RUN curl -sL --no-verbose https://archive.apache.org/dist/tomcat/tomcat-8/v8.0.26/bin/apache-tomcat-8.0.26.tar.gz -o /tmp/tomcat.tar.gz && \
    curl -sL --no-verbose http://ftp-devel.mzk.cz/jre/jdk-7u75-linux-x64.tar.gz -o /tmp/java.tar.gz
RUN mkdir -p /usr/local/java
ENV JAVA_HOME /usr/local/java/jdk1.7.0_75
ENV CATALINA_HOME /usr/local/tomcat
RUN tar xzf /tmp/java.tar.gz --directory=/usr/local/java

RUN tar xzf /tmp/tomcat.tar.gz --directory=/usr/local/ && mv /usr/local/apache-tomcat-* /usr/local/tomcat
ENV PATH  $CATALINA_HOME/bin:$JAVA_HOME/bin:$PATH


RUN curl -sL --no-verbose https://github.com/moravianlibrary/MEditor/archive/master.zip -o /tmp/meditor.zip 
RUN curl -sL  http://ftp-devel.mzk.cz/meditor/master/meditor.war -o $CATALINA_HOME/webapps/meditor.war
RUN curl -sL https://jdbc.postgresql.org/download/postgresql-9.4-1201.jdbc41.jar -o $CATALINA_HOME/lib/postgresql-9.4-1201.jdbc41.jar
RUN unzip /tmp/meditor.zip -d /tmp/meditor/

#in archive is duplicated adore-djatoka-1.1 (so overwrite)
RUN unzip -o /tmp/meditor/MEditor-master/resources/djatoka/dist/djatoka.war -d $CATALINA_HOME/webapps/djatoka


RUN mkdir -p /root/.meditor

RUN mv /tmp/meditor/MEditor-master/resources/djatoka /root/.meditor/djatoka
RUN mv /tmp/meditor/MEditor-master/resources/xml /root/.meditor/xml
RUN mv /tmp/meditor/MEditor-master/resources/convertAudio /root/.meditor/convertAudio
ADD djatoka.properties $CATALINA_HOME/webapps/djatoka/WEB-INF/classes/djatoka.properties
ADD log4j.properties $CATALINA_HOME/webapps/djatoka/WEB-INF/classes/log4j.properties

ENV DJATOKA_HOME /root/.meditor/djatoka
ENV LIBPATH $DJATOKA_HOME/lib
ENV LD_LIBRARY_PATH $LIBPATH/Linux-x86-64
ENV KAKADU_HOME $DJATOKA_HOME/bin/Linux-x86-64
ENV KAKADU_LIBRARY_PATH -DLD_LIBRARY_PATH=$LIBPATH/Linux-x86-64
ENV JAVA_OPTS -Dfile.encoding=UTF8 -Djava.awt.headless=true -Dfile.encoding=UTF8 -XX:MaxPermSize=256m -Xms1024m -Xmx3072m -Dkakadu.home=$KAKADU_HOME -Djava.library.path=$LIBPATH/Linux-x86-64 $KAKADU_LIBRARY_PATH

#TLS
RUN keytool -genkey -alias tomcat  -dname "CN=localhost, OU=mzk, S=cz, C=cz" -keyalg RSA -storepass somekey -keypass somekey
ADD rewrite.config $CATALINA_HOME/conf/Catalina/localhost/
ADD server.xml $CATALINA_HOME/conf/
ADD tomcat-users.xml /tmp/tomcat-users.xml

# want empty properties configuration
RUN touch /root/.meditor/configuration.properties
ADD ldap.properties /root/.meditor/ldap.properties

EXPOSE 8080
EXPOSE 8843

ADD docker-entrypoint.sh /
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["catalina.sh", "run"]

# default conf
ENV hostanme=localhost

