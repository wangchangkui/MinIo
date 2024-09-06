FROM eclipse-temurin:17.0.8_7-jre-centos7
ENV PORT 8523
ENV JARNAME MinIoUtil-0.0.1-SNAPSHOT.jar
ENV XMX 4096m
ENV XMS 512m
ENV TZ Asia/Shanghai
WORKDIR /src
EXPOSE ${PORT}
ADD  ./${JARNAME} /src/${JARNAME}
CMD ["/bin/bash", "-c", "java -Xms${XMS} -Xmx${XMX}  -jar /src/${JARNAME}"]
