FROM martinrumanek/meditor


RUN apt-get install libfcgi0ldbl libjpeg62 libmemcached10 libtiff4 libapache2-mod-fcgid

# iipsrv
RUN dpkg -i /tmp/iipimage-jp2-amd64.deb
ADD index.html /var/www/index.html
ADD js /var/www/js

ADD meditor-site /etc/apache2/sites-enabled/000-default