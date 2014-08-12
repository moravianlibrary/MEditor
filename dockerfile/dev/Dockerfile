FROM meditor-iipsrv

RUN sed 's/host    all             all             127.0.0.1\/32            md5/host    all             all             0.0.0.0\/0            md5/' \
  /etc/postgresql/9.1/main/pg_hba.conf > pg_hba.conf && \
    mv pg_hba.conf /etc/postgresql/9.1/main/pg_hba.conf

RUN sed "s/#listen_addresses = 'localhost'/listen_addresses = '*'/" \
  /etc/postgresql/9.1/main/postgresql.conf > postgresql.conf && \
    mv postgresql.conf /etc/postgresql/9.1/main/postgresql.conf

ADD tomcat-users.xml /home/meditor/tomcat/conf/tomcat-users.xml

