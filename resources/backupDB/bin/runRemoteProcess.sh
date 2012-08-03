#!/bin/bash
# author Matous Jobanek
# script for running process via ssh on the remote machine
# variables:
#	1. username (Mandatory)
#	2. the machine location (M)
#	3. the command (M)
#	4. location of the output file (Optional)

if [ $# -ne 3 ] && [ $# -ne 4 ]; then 
	echo "ERROR: Missing arguments"
	echo "Usage: runRemoteScript.sh username machine_location command output_file(is_optional)"
	exit 5
fi

if [ -n $4 ]; then
	OUT=$(dirname ${4})
	if [ ! -d "$OUT" ]; then
		echo "ERROR: Directory "$OUT" does not exist!"	
		exit 4
	fi
	ssh $1@$2 $3 > $4
else
	ssh $1@$2 $3
fi

exit 0
