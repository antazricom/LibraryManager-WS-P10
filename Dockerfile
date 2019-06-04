FROM tomcat:9.0.14-jre8
MAINTAINER antazri.xyz

COPY ./webservice/target/webservice-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/webservice.war

ADD tomcat-users.xml /usr/local/tomcat/conf/

CMD ["catalina.sh", "run"]