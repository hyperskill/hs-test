FROM gradle:5.3.1-jdk11-slim

USER root
COPY . hs-test
RUN chmod 777 /home/gradle/hs-test

USER gradle
WORKDIR /home/gradle/hs-test
RUN gradle resolveDependencies