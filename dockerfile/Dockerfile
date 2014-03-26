FROM debian

MAINTAINER Martin Rumanek <martin@rumanek.cz>

# install packages
RUN apt-get -y  update 
RUN apt-get -y install wget postgresql apache2 libapache2-mod-jk vim unzip curl libtiff-tools imagemagick sox ffmpeg

ADD schema.sql /tmp/schema.sql
RUN sed 's/local   all             all                                     peer/local   all             all                                     md5/' \
  /etc/postgresql/9.1/main/pg_hba.conf > pg_hba.conf && \
    mv pg_hba.conf /etc/postgresql/9.1/main/pg_hba.conf && \
  /etc/init.d/postgresql start && su -l postgres -c "createuser meditor --no-superuser --no-createdb --no-createrole; \
 createdb meditor --owner=meditor; \
 psql --command \"alter user \\\"meditor\\\" with password 'meditor';\"; psql -f /tmp/schema.sql meditor"

RUN wget --no-verbose http://mirror.hosting90.cz/apache/tomcat/tomcat-7/v7.0.52/bin/apache-tomcat-7.0.52.tar.gz -O /tmp/tomcat.tar.gz && \
    wget --no-verbose https://github.com/moravianlibrary/MEditor/archive/master.zip -O /tmp/meditor.zip && \
    wget --no-verbose https://dl.dropbox.com/s/9zcorgucorgfe3e/jdk-7u51-linux-x64.tar.gz -O /tmp/java.tar.gz
RUN mkdir /usr/java
RUN tar xzf /tmp/java.tar.gz --directory=/usr/java/

RUN adduser --disabled-password --gecos "" meditor
USER meditor
ENV JAVA_HOME /usr/java/jdk1.7.0_51
ENV PATH $JAVA_HOME/bin:$PATH
RUN tar xzf /tmp/tomcat.tar.gz --directory=/home/meditor/
ENV CATALINA_HOME /home/meditor/apache-tomcat-7.0.52
RUN wget --no-verbose https://dl.dropbox.com/s/txg6h2f0ws3e2fs/meditor.war -O /home/meditor/apache-tomcat-7.0.52/webapps/meditor.war
RUN wget --no-verbose http://jdbc.postgresql.org/download/postgresql-9.1-903.jdbc4.jar -O /home/meditor/apache-tomcat-7.0.52/lib/postgresql-9.1-903.jdbc4.jar
RUN unzip /tmp/meditor.zip -d /tmp/meditor/
RUN mv /tmp/meditor/MEditor-master/resources/djatoka/dist/djatoka.war /home/meditor/apache-tomcat-7.0.52/webapps/djatoka.war

USER root
RUN a2enmod rewrite && a2enmod jk
ADD workers.properties /etc/libapache2-mod-jk/workers.properties
ADD meditor-site /etc/apache2/sites-enabled/000-default

RUN apt-get -y install ssl-cert openssl
RUN mkdir /etc/apache2/ssl
RUN openssl req -x509 -nodes -days 1095 -newkey rsa:2048 -keyout /etc/apache2/ssl/apache.key -out /etc/apache2/ssl/apache.crt -subj "/C=CZ/ST=Czech Republic/L=Brno/O=Moravian Library/CN=localhost"
RUN a2enmod ssl

USER meditor
RUN mkdir -p /home/meditor/.meditor
ADD configuration.properties /home/meditor/.meditor/configuration.properties
ADD ldap.properties /home/meditor/.meditor/ldap.properties

RUN /home/meditor/apache-tomcat-7.0.52/bin/startup.sh && sleep 60
RUN mv /tmp/meditor/MEditor-master/resources/djatoka /home/meditor/.meditor/djatoka
RUN mv /tmp/meditor/MEditor-master/resources/xml /home/meditor/.meditor/xml
ADD djatoka.properties /home/meditor/apache-tomcat-7.0.52/webapps/djatoka/WEB-INF/classes/djatoka.properties


ENV DJATOKA_HOME /home/meditor/.meditor/djatoka
ENV LIBPATH $DJATOKA_HOME/lib
ENV LD_LIBRARY_PATH $LIBPATH/Linux-x86-64
ENV KAKADU_HOME $DJATOKA_HOME/bin/Linux-x86-64
ENV KAKADU_LIBRARY_PATH -DLD_LIBRARY_PATH=$LIBPATH/Linux-x86-64
ENV JAVA_OPTS -Djava.awt.headless=true -Dkakadu.home=$KAKADU_HOME -Djava.library.path=$LIBPATH/Linux-x86-64 $KAKADU_LIBRARY_PATH

USER root
ADD initData.sh /initData.sh

ENV CATALINA_OPTS "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"

ENTRYPOINT /initData.sh && /etc/init.d/postgresql start && /etc/init.d/apache2 start && su -l -m meditor -c "/home/meditor/apache-tomcat-7.0.52/bin/startup.sh" && /bin/bash

