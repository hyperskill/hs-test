FROM gradle:8.5-jdk21

COPY --chown=gradle . hs-test
WORKDIR /home/gradle/hs-test
RUN gradle resolveDependencies
