#!/bin/bash
set -x

USER=kremser

#adduser
grep "^meditor:" /etc/passwd || sudo adduser meditor

#packages
sudo aptitude install libtiff-tools imagemagick sshfs wget

#switch
sudo su meditor
cd ~
echo "syntax on" >> ~/.vimrc
# bash promenne nastavit

#ssh key
if [ ! -e ~/.ssh/id_rsa.pub ] ; then
  ssh-keygen -t rsa 
fi

#key distribution
ssh $USER@editor-devel.mzk.cz "echo `cat ~/.ssh/id_rsa.pub` >> /home/meditor/.ssh/authorized_keys"
#...

mkdir install
cd install

wget http://download.akka.io/downloads/akka-2.0.1.tgz


tar -xf akka-2.0.1.tgz

set +x
