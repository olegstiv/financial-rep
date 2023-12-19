FROM gradle:8.1.1-jdk21

WORKDIR /app

COPY . .

RUN ./gradlew build

RUN chmod +x .ci/entrypoint.sh

CMD ["/bin/bash", "-c", ".ci/entrypoint.sh"]
