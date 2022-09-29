aws s3 cp s3://java-artifacts-vanquishquest/target/project_2-0.1.1-SNAPSHOT.war /tmp
sudo mv/tmp/Vanquishquest.war /usr/share/tomcat/webapps/project_2-0.1.1-SNAPSHOT.war
sudo service tomcat restart
