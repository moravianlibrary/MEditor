FROM ubuntu:latest

MAINTAINER Martin Rumanek <martin@rumanek.cz>

ENV REFRESHED_AT 2015-02-11

RUN apt-get -y install postgresql-client sed
ADD crontab /etc/cron.d/db-backup
RUN chmod 0644 /etc/cron.d/db-backup

RUN touch /var/log/cron.log

ADD config.sh config.sh

CMD ./config.sh; cron && tail -f /var/log/cron.log
