FROM maven:3.9.6-amazoncorretto-17 build
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM amazoncorretto:17.0.10
RUN mkdir /app
RUN apt-get update && apt-get install -y dumb-init
COPY --from=build /app/target/pagamentos-*.jar /app/java-application.jar
WORKDIR /app
RUN addgroup --system pagamentos-app && useradd -r pagamentos-app -g pagamentos-app
RUN chown -R pagamentos-app:pagamentos-app /app
USER pagamentos-app
EXPOSE 8084
CMD "dumb-init" "java" "-jar" "java-application.jar"
