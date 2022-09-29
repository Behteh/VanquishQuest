aws s3 cp s3://java-artifacts-vanquishquest/target/project_2-0.0.1-SNAPSHOT.jar /tmp
sudo mv/tmp/project_2-0.0.1-SNAPSHOT.jar /home/ec2-user/project_2-0.0.1-SNAPSHOT.jar
nohup java -jar project_2-0.0.1-SNAPSHOT.jar
