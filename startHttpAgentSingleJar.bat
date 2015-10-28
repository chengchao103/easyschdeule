cd es-httpagent
rem call mvn assembly:assembly
cd target
java -jar es-httpagent-1.0.1-SNAPSHOT-jar-with-dependencies.jar
pause