FROM gradle:9.6.1-jdk25

COPY --chown=gradle . hs-test
WORKDIR /home/gradle/hs-test
RUN gradle resolveDependencies
