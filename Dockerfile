FROM maven as build

WORKDIR /workspace/app
# в эту папку копируем наш pom.xml
COPY pom. xml.
# также в эту папку копируем наш исходный код
COPY src src

RUN mvn clean package

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*jar)

FROM eclipse-temurin:17-jre-alpine

ARG DEPENDENCY=/workspace/app/target/dependency
# из предыдущего шага забираем все зависимости и копируем их в новый linux
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
#вапускаем приложение вместе со всеми библиотеками
ENTRYPOINT ["java","-cp", "app:app/lib/*", "ait.cohort46.PetsCareApplication"]