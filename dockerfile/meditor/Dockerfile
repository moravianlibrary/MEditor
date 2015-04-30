FROM tomcat:jre8 

MAINTAINER Martin Rumanek <martin@rumanek.cz>

ENV REFRESHED_AT 2015-04-30

## install packages
RUN apt-get -y  update && apt-get -y install unzip curl libtiff-tools imagemagick sox ffmpeg libsox-fmt-mp3 


RUN curl -sL --no-verbose https://github.com/moravianlibrary/MEditor/archive/master.zip -o /tmp/meditor.zip 

RUN curl -sL  http://ftp-devel.mzk.cz/meditor/master/meditor.war -o $CATALINA_HOME/webapps/meditor.war

RUN  curl -sL https://jdbc.postgresql.org/download/postgresql-9.4-1201.jdbc41.jar -o $CATALINA_HOME/lib/postgresql-9.4-1201.jdbc41.jar
RUN unzip /tmp/meditor.zip -d /tmp/meditor/

#in archive is duplicated adore-djatoka-1.1 (so overwrite)
RUN unzip -o /tmp/meditor/MEditor-master/resources/djatoka/dist/djatoka.war -d $CATALINA_HOME/webapps/djatoka

RUN mkdir -p /root/.meditor
ADD configuration.properties /root/.meditor/configuration.properties
ADD ldap.properties /root/.meditor/ldap.properties

RUN mv /tmp/meditor/MEditor-master/resources/djatoka /root/.meditor/djatoka
RUN mv /tmp/meditor/MEditor-master/resources/xml /root/.meditor/xml
RUN mv /tmp/meditor/MEditor-master/resources/convertAudio /root/.meditor/convertAudio
ADD djatoka.properties /root/.meditor/tomcat/webapps/djatoka/WEB-INF/classes/djatoka.properties
ADD log4j.properties /root/.meditor/tomcat/webapps/djatoka/WEB-INF/classes/log4j.properties

ENV DJATOKA_HOME /root/.meditor/djatoka
ENV LIBPATH $DJATOKA_HOME/lib
ENV LD_LIBRARY_PATH $LIBPATH/Linux-x86-64
ENV KAKADU_HOME $DJATOKA_HOME/bin/Linux-x86-64
ENV KAKADU_LIBRARY_PATH -DLD_LIBRARY_PATH=$LIBPATH/Linux-x86-64
ENV JAVA_OPTS -Dfile.encoding=UTF8 -Djava.awt.headless=true -Dfile.encoding=UTF8 -XX:MaxPermSize=256m -Xms1024m -Xmx3072m -Dkakadu.home=$KAKADU_HOME -Djava.library.path=$LIBPATH/Linux-x86-64 $KAKADU_LIBRARY_PATH


# comment this out if you want to disable debug mode
ENV CATALINA_OPTS "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"


#RUN mv /home/meditor/.meditor /home/meditor/.meditorDefault && ln -s /meditor-data/.meditor /home/meditor/.meditor \
#    && rm -r /home/meditor/output/ && ln -s /meditor-data/.meditor/output /home/meditor/output

#TLS
RUN keytool -genkey -alias tomcat  -dname "CN=localhost, OU=mzk, S=cz, C=cz" -keyalg RSA -storepass changeit -keypass changeit
ADD rewrite.config $CATALINA_HOME/conf/Catalina/localhost/
ADD server.xml $CATALINA_HOME/conf/

ADD docker-entrypoint.sh /
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["catalina.sh", "run"]

