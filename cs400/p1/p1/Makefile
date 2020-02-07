.PHONY =  junit5 junit4

junit5: DataStructureADTTest.java
	javac -cp .:./classes/:junit-platform-console-standalone-1.3.2.jar *.java

my: junit5
	java -jar junit-platform-console-standalone-1.3.2.jar --class-path .:./classes/ -c TestDS_My 

andy: junit5
	java -jar junit-platform-console-standalone-1.3.2.jar --class-path .:./classes/ -c TestDS_Andy

deb: junit5
	java -jar junit-platform-console-standalone-1.3.2.jar --class-path .:./classes/ -c TestDS_Deb

all: junit5
	java -jar junit-platform-console-standalone-1.3.2.jar --class-path .:./classes/ -p ""

junit4:
	javac -cp .:./classes/:junit4-12.jar *.java
	java cp .:./classes/:junit4-12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore TestDS_My 

