FROM gradle:5.3.1-jdk11-slim

COPY --chown=gradle . hs-test
WORKDIR /home/gradle/hs-test
RUN gradle resolveDependencies
