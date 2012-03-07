#!/bin/bash
for file in *.java
do
   mv ${file} "`basename $file .java`Client.java"
done
