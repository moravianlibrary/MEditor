FROM meditor

RUN echo "Version: 20140716"

RUN apt-get -y install libfcgi0ldbl libjpeg62 libmemcached10 libtiff4 libapache2-mod-fcgid

RUN wget --no-verbose http://dl.dropbox.com/s/7k24kkvqf1pafec/iipimage-jp2-amd64.deb -O /tmp/iipimage-jp2-amd64.deb

# iipsrv
RUN dpkg -i /tmp/iipimage-jp2-amd64.deb
ADD index.html /var/www/index.html
ADD js /var/www/js

ADD meditor-site /etc/apache2/sites-enabled/000-default
