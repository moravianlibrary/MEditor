FROM debian:wheezy

MAINTAINER Martin Rumanek <martin@rumanek.cz>

RUN echo "Version: 20140716"

## install packages
ENV PATH /usr/bin:/bin:/usr/sbin:/sbin
ENV DEBIAN_FRONTEND noninteractive

# install packages
RUN apt-get -y  update; apt-get -y install wget postgresql apache2 libapache2-mod-jk \
	vim unzip curl libtiff-tools imagemagick sox ffmpeg libsox-fmt-mp3 \
	less

ADD schema.sql /tmp/schema.sql
RUN sed 's/local   all             all                                     peer/local   all             all                                     md5/' \
  /etc/postgresql/9.1/main/pg_hba.conf > pg_hba.conf && \
    mv pg_hba.conf /etc/postgresql/9.1/main/pg_hba.conf && \
  /etc/init.d/postgresql start && su -l postgres -c "createuser meditor --no-superuser --no-createdb --no-createrole; \
 createdb meditor --owner=meditor; \
 psql --command \"alter user \\\"meditor\\\" with password 'meditor';\"; psql -f /tmp/schema.sql meditor"

RUN wget --no-verbose http://mirror.hosting90.cz/apache/tomcat/tomcat-7/v7.0.55/bin/apache-tomcat-7.0.55.tar.gz -O /tmp/tomcat.tar.gz && \
    wget --no-verbose https://github.com/moravianlibrary/MEditor/archive/master.zip -O /tmp/meditor.zip && \
    wget --no-verbose https://dl.dropbox.com/s/9zcorgucorgfe3e/jdk-7u51-linux-x64.tar.gz -O /tmp/java.tar.gz
RUN mkdir /usr/java
RUN tar xzf /tmp/java.tar.gz --directory=/usr/java/

RUN adduser --disabled-password --gecos "" meditor
USER meditor
ENV JAVA_HOME /usr/java/jdk1.7.0_51
ENV PATH $JAVA_HOME/bin:$PATH
RUN tar xzf /tmp/tomcat.tar.gz --directory=/home/meditor/ && mv /home/meditor/apache-tomcat-* /home/meditor/tomcat
ENV CATALINA_HOME /home/meditor/tomcat
RUN wget --no-verbose https://dl.dropbox.com/s/txg6h2f0ws3e2fs/meditor.war -O /home/meditor/tomcat/webapps/meditor.war
RUN wget --no-verbose http://jdbc.postgresql.org/download/postgresql-9.1-903.jdbc4.jar -O /home/meditor/tomcat/lib/postgresql-9.1-903.jdbc4.jar
RUN unzip /tmp/meditor.zip -d /tmp/meditor/
RUN mv /tmp/meditor/MEditor-master/resources/djatoka/dist/djatoka.war /home/meditor/tomcat/webapps/djatoka.war

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

RUN /home/meditor/tomcat/bin/startup.sh && sleep 60
RUN mv /tmp/meditor/MEditor-master/resources/djatoka /home/meditor/.meditor/djatoka
RUN mv /tmp/meditor/MEditor-master/resources/xml /home/meditor/.meditor/xml
ADD djatoka.properties /home/meditor/tomcat/webapps/djatoka/WEB-INF/classes/djatoka.properties
ADD log4j.properties /home/meditor/tomcat/webapps/djatoka/WEB-INF/classes/log4j.properties

ENV DJATOKA_HOME /home/meditor/.meditor/djatoka
ENV LIBPATH $DJATOKA_HOME/lib
ENV LD_LIBRARY_PATH $LIBPATH/Linux-x86-64
ENV KAKADU_HOME $DJATOKA_HOME/bin/Linux-x86-64
ENV KAKADU_LIBRARY_PATH -DLD_LIBRARY_PATH=$LIBPATH/Linux-x86-64
ENV JAVA_OPTS -Dfile.encoding=UTF8 -Djava.awt.headless=true -Dfile.encoding=UTF8 -XX:MaxPermSize=256m -Xms1024m -Xmx3072m -Dkakadu.home=$KAKADU_HOME -Djava.library.path=$LIBPATH/Linux-x86-64 $KAKADU_LIBRARY_PATH
ENV PATH /usr/java/jdk1.7.0_51/bin:$PATH


USER root
ADD init.sh /init.sh

# comment this out if you want to disable debug mode
ENV CATALINA_OPTS "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"

RUN sed "s/data_directory = '\/var\/lib\/postgresql\/9.1\/main'/data_directory = '\/meditor-data\/postgres-data\/main'/" /etc/postgresql/9.1/main/postgresql.conf > /tmp/postgresql.conf
RUN mv /tmp/postgresql.conf /etc/postgresql/9.1/main/postgresql.conf
RUN mv /home/meditor/.meditor /home/meditor/.meditorDefault && ln -s /meditor-data/.meditor /home/meditor/.meditor \
    && rm -r /home/meditor/output/ && ln -s /meditor-data/.meditor/output /home/meditor/output

# workaround files owner problem
RUN chown -R meditor:meditor /home/meditor/
