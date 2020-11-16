FROM maven:3.6-jdk-11-slim

WORKDIR /app

COPY . /app

RUN mvn clean compile assembly:single

ENV projectPath=""

ENV fileConfig=""

ENV ignorer=""

ENV outputFile=""

ENTRYPOINT java -DprojectPath=$projectPath -DfileConfig=$fileConfig -Dignorer=$ignorer -DoutputFile=$outputFile -jar ./target/logGenerator-jar-with-dependencies.jar
