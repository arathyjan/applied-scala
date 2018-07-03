FROM openjdk:8u171-jre

ARG build_version

EXPOSE 8080

# Include "shush" to decode KMS_ENCRYPTED_STUFF
RUN curl -sL -o /usr/local/bin/shush \
    https://github.com/realestate-com-au/shush/releases/download/v1.3.4/shush_linux_amd64 \
 && chmod +x /usr/local/bin/shush
ENTRYPOINT ["/usr/local/bin/shush", "exec", "--"]

ENV NEW_RELIC_ENV production
ENV JAVA_OPTS "-javaagent:newrelic/newrelic.jar"
ENV NEWRELIC_VERSION 3.48.0
ENV VERSION=$build_version

RUN curl -sL -o newrelic-java-${NEWRELIC_VERSION}.zip \
    https://repo1.maven.org/maven2/com/newrelic/agent/java/newrelic-java/${NEWRELIC_VERSION}/newrelic-java-${NEWRELIC_VERSION}.zip \
 && unzip newrelic-java-${NEWRELIC_VERSION}.zip newrelic/newrelic.jar -d /app \
 && rm -f newrelic-java-${NEWRELIC_VERSION}.zip

WORKDIR /app

ADD newrelic/newrelic.yml /app/newrelic/
ADD target/app.jar /app/
ADD support/prod/run /app/

RUN groupadd -r appuser && useradd -r -m -g appuser appuser

USER appuser

CMD exec ./run
