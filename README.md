# zipkin-demo说明
## 环境准备
- 本地安装docker desktop
- docker命令安装zipkin: docker run -d -p 9411:9411 openzipkin/zipkin
- 验证服务启动，访问URL: http://localhost:9411确认即可

## 微服务准备
- 编译四个demo服务，编译示例: cd demoN && mvn clean package
- 启动服务四个demo服务
  ```
  demo1: java -D"server.port=8081" -jar .\target\demo1-1.0-SNAPSHOT.jar
  demo2: java -D"server.port=8082" -jar .\target\demo2-1.0-SNAPSHOT.jar
  demo3: java -D"server.port=8083" -jar .\target\demo3-1.0-SNAPSHOT.jar
  demo4: java -D"server.port=8084" -jar .\target\demo4-1.0-SNAPSHOT.jar
  ```
- 测试服务，访问URL： http://localhost:8081/demo1/zipkin/test
- 验证zipkin收集数据: 访问URL： http://localhost:9411 ，点击查询，看到数据便说明正常
