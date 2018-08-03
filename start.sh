#!/usr/bin/env bash
MAN="Usage: $(basename "$0") [-h] [-f] [-p proxyurl] -- starts container and deploys application
    where:
        -h show this help
        -f force rebuild
        -p use proxy with url <proxyurl>"

REBUILD="false"
PROXYBASE=""
WARFILE=./SWBP/target/SWBP-5.0-SNAPSHOT.war
ENCODING=iso8859-1

while getopts ":hfp:" opt; do
    case $opt in
        f)
            REBUILD="true"
            ;;
        p)
            PROXYBASE="--proxy-base-url $OPTARG"
            ;;
        h)
            echo "$MAN"
            exit 0;
            ;;
        \?)
            echo "Invalid option: -$OPTARG" >&2
            exit 1
            ;;
        :)
            echo "Argument missing for option -$OPTARG." >&2
            exit 1
            ;;
    esac
done

if [ "$REBUILD" = "true" ]; then
    echo "Recompiling project..."
    sh ./install.sh
fi

if [ ! -e "$WARFILE" ]; then
    echo "WAR file not found. Run start.sh -f."
else
    java -Dfile.encoding=$ENCODING -jar target/dependency/webapp-runner.jar $PROXYBASE $WARFILE
fi