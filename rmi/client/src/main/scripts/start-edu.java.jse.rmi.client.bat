
set DIST_DIR_PATH=d:/projects/edu/workspace/edu.java.jse.rmi/edu.java.jse.rmi.client/target

set JAVA_CLASSPATH=.\edu.java.jse.rmi.client-0.0.1-SNAPSHOT.jar
set JAVA_CLASSPATH=%JAVA_CLASSPATH%;.\lib\edu.java.jse.rmi.api-0.0.1-SNAPSHOT.jar

set JAVA_CODE_BASE=file:/%DIST_DIR_PATH%/lib/edu.java.jse.rmi.client-0.0.1-SNAPSHOT.jar
set JAVA_MAIN_CLASS=edu.java.jse.rmi.client.example.HelloTaskExecutorServiceMain

set JAVA_MAIN_CLASS_PARAMS=localhost "Dear Friend"

set SECURITY_POLICY_PATH=classes\client.policy

java -cp %JAVA_CLASSPATH% -Djava.security.policy=%SECURITY_POLICY_PATH% -Djava.rmi.server.codebase=%JAVA_CODE_BASE% %JAVA_MAIN_CLASS% %JAVA_MAIN_CLASS_PARAMS%

pause
