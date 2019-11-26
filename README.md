# tcp协议栈开发

1. [JDK1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. [Netty-4.1.42.Final](https://netty.io/wiki/)
3. [protobuf-java 3.9](https://github.com/protocolbuffers/protobuf) 
<hr>

### 消息头

| 名称 |长度|数据|说明|
|:---:|:---:|:---:|:---:|
| magic |4 |0x0CAFFEE0|唯一通信标志 |
| length |4|0x16+body.length |消息总长度|
| moduleId | 4|-|功能大模块|
| cmdId  |4|-|具体功能|
| body  |-|body.length|消息体|

### 注解说明

| 名称  | 说明 |
|:---:|:---:|
| ScanMapping  | 控制器的包路径 |
| ReqMapping  | 标记协议号的分发   | 
| AutoIn  | 对象自动注入  |
| MapperScan  | 扫描mapper层,标记为操作db的对象  |
| ServiceImpl  | 标记需要service服务实现的interface对象|

### 模块说明

- common
```text
项目中的公共模块
```
- msg-proto
```text
1. 协议栈的消息id编号
2. 消息对象
3. protoBuffer生成的对象
```
- msg-template
```text
生成xml配置的协议消息
可打出jar包单独拎出来执行
resources的msg-id为协议消息目录,可以直接执行jar文件
```
- server-basic
```text
1. DbConnectionPool
2. netty的handler
3. 监听器
4. 协议消息注册中心
5. 消息分发处理
```
- protobuf
```text
一个放proto文件的文件夹
proto文件编译可执行的java对象
```
- server-system
```text
具体的功能实现模块
1. guava缓存
2. 控制器controller的实现
3. 监听器的实现
4. service服务对象实现
5. mapper操作DB

```
- server-all
```text
服务启动
```
- client-test

<hr>

- 服务启动
```text
通过MAIN方法启动 com.github.chenmingq.server.all.ServerStart
```
```java
@ScanMapping(name = "com.github.chenmingq.server.system.controller")
public class ServerStart {
    public static void main(String[] args) {
         Server.startServer(ServerStart.class, args);
    }
}
```

- 客户端测试
```text
通过MAIN方法启动 com.github.chenmmingq.client.test.ClientStart
```
```java
// com.github.chenmmingq.client.test.ClientAll
// 具体实现客户端创建的请求功能
```

##### 打包服务
```shell script
  cd socket-test 
  mvn clean package
```

##### 启动服务
```shell script
    cd socket-test/server-all/target
    java -jar server-all-1.0-SNAPSHOT-jar-with-dependencies.jar
```

###### demo
1. 实现了客户端登陆测试
2. 实现了客户端用户注册测试

### 服务端功能

1. 存储二进制数据
2. guava缓存
3. session管理
4. 监听器
5. protobuf序列化数据传输
6. 集成mybatis
7. Executor线程实现
8. 服务端心跳服务
9. 通过注解实现TCP协议栈消息

### 该项目主要是用来自己学习使用,请多多指教