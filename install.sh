aws s3 cp s3://java-artifacts-vanquishquest/target/Vanquishquest.war /tmp
sudo mv/tmp/Vanquishquest.war /usr/share/tomcat/webapps/Vanquishquest.war
sudo service tomcat restart
