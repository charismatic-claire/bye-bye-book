FROM nginx
#WORKDIR ../../../src/bbb-client/ 
#RUN npm install
#RUN npm run build
#WORKDIR ../../docker/bbb/client/
RUN mkdir /app
COPY conf/nginx.conf /etc/nginx/nginx.conf
EXPOSE 8080
