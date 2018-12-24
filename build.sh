mvn clean
cp pom.xml pom.xml.bak
mv src/test src/test__
cp pomVisor.xml pom.xml
mvn package 
cp target/sm3-1.0-SNAPSHOT.jar ./SVisor.jar
cp pomMain.xml pom.xml
mvn clean
mvn package
mv target/sm3-1.0-SNAPSHOT.jar target/ScalaSM3.jar
mv SVisor.jar target/SVisor.jar
cp pom.xml.bak pom.xml
mv src/test__ src/test
