#!/usr/bin/env bash
MAN="Usage: $(basename "$0") [-h|f|c] [-p proxyurl] -- starts container and deploys application
    where:
        -h show this help
        -f force rebuild
        -c copy assets
        -p use proxy with url <proxyurl>"

REBUILD="false"
COPYASSETS="false"
PROXYBASE=""
WARFILE=./SWBP/target/SWBP-5.0-SNAPSHOT.war
ENCODING=iso8859-1
ASSETSFOLDER=SWBP/src/main/webapp/swbadmin/jsp/process
EXPANDEDAPPROOT=target/tomcat.8080/webapps/expanded

while getopts ":hfcp:" opt; do
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
        c)
            COPYASSETS="true"
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
    echo "Starting container..."
    if [ "$COPYASSETS" = "true" ]; then
        cp -r $ASSETSFOLDER target/tomcat.8080/webapps/expanded/swbadmin/jsp/process
        java -Dfile.encoding=$ENCODING -jar target/dependency/webapp-runner.jar $PROXYBASE $EXPANDEDAPPROOT
    else
        java -Dfile.encoding=$ENCODING -jar target/dependency/webapp-runner.jar $PROXYBASE $WARFILE
    fi
fi