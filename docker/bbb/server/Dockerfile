FROM openjdk:8
#WORKDIR ../../../src/bbb-server/ 
#RUN .gradlew build 
#WORKDIR ../../docker/bbb/server/
COPY bash/* /
RUN mkdir -p /usr/bin/server && mkdir -p /var/lib/server/files
WORKDIR /usr/bin/server
CMD ["/startup_prod.sh"]
