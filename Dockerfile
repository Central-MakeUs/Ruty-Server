# java 17버전으로 이미지 생성하기
FROM openjdk:17-jdk

# 디렉토리 생성 및 이동
WORKDIR /app

# Copy build artifact (JAR)
COPY build/libs/luty-server-0.0.1-SNAPSHOT.jar /app/app.jar

# Set environment variables
ENV JAVA_OPTS="-Dserver.port=8080"
ENV PORT=8080

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
