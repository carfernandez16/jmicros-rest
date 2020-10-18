
# JMicros

Repository with examples of Java

## Build Prerequisites
1. corretto 1.8
2. gradle 3.4.1
3. jdk 1.8


## Build
```
gradle clean assemble fatjar && docker build -t jmicros .
```

## Execute
```
docker-compose up jmicros
```