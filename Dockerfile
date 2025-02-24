# OpenJDK 17을 기반 이미지로 사용
FROM openjdk:17-alpine

# JAR 파일을 컨테이너에 복사
COPY ./build/libs/docstory-0.0.1-SNAPSHOT.jar /app/docstory.jar

# 실행 명령어
ENTRYPOINT ["java", "-jar", "/app/docstory.jar"]