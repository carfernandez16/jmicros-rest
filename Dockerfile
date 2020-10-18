FROM amazoncorretto

MAINTAINER Carlos Fernandez <croberto.fernandez@gmail.com>

RUN echo "building container for program JMicros"

WORKDIR /usr/app1

COPY build/libs/jmicros-1.0.jar /usr/app1/

ENTRYPOINT ["java", "-cp", "jmicros-1.0.jar", "com.jmicros.Main"]