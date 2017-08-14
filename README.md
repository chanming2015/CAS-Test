# CAS-Test 简单例子
* **CAS 介绍**  
CAS 是 Yale 大学发起的一个开源项目，旨在为 Web 应用系统提供一种可靠的单点登录方法，CAS 在 2004 年 12 月正式成为 JA-SIG 的一个项目。
---
* **CAS 原理和协议**  
从结构上看，CAS 包含两个部分： CAS Server 和 CAS Client。CAS Server 需要独立部署，主要负责对用户的认证工作；CAS Client 负责处理对客户端受保护资源的访问请求，需要登录时，重定向到 CAS Server。图1 是 CAS 最基本的协议过程：  
![CAS基本协议图](cas_protocol.jpg)  
CAS Client 与受保护的客户端应用部署在一起，以 Filter 方式保护受保护的资源。对于访问受保护资源的每个 Web 请求，CAS Client 会分析该请求的 Http 请求中是否包含 Service Ticket，如果没有，则说明当前用户尚未登录，于是将请求重定向到指定好的 CAS Server 登录地址，并传递 Service （也就是要访问的目的资源地址），以便登录成功过后转回该地址。用户在第 3 步中输入认证信息，如果登录成功，CAS Server 随机产生一个相当长度、唯一、不可伪造的 Service Ticket，并缓存以待将来验证，之后系统自动重定向到 Service 所在地址，并为客户端浏览器设置一个 Ticket Granted Cookie（TGC），CAS Client 在拿到 Service 和新产生的 Ticket 过后，在第 5，6 步中与 CAS Server 进行身份合适，以确保 Service Ticket 的合法性。  
在该协议中，所有与 CAS 的交互均采用 SSL 协议，确保，ST 和 TGC 的安全性。协议工作过程中会有 2 次重定向的过程，但是 CAS Client 与 CAS Server 之间进行 Ticket 验证的过程对于用户是透明的。  
另外，CAS 协议中还提供了 Proxy （代理）模式，以适应更加高级、复杂的应用场景，具体介绍可以参考 [CAS 官方网站](https://www.apereo.org/projects/cas)上的相关文档。
---
* **部署 CAS Server**  
部署过程[参考文档](https://github.com/apereo/cas-overlay-template)  
使用package命令打包得到cas.war，这个war包可以直接使用 **java -jar cas.war** 这个命令启动，而且还会自动加载外部配置文件（/etc/cas/这个目录）。  
如果要修改默认的配置，只需要修改工程目录下的etc/cas/config/application.yml文件，例如要修改https证书的密码（war包里面的默认密码是changeit）则需要添加如下内容：  
`server:
  ssl:
    key-store-password: "xxx"
    key-password: "xxx"`此处的xxx为实际创建证书时使用的密码。  
服务端启动前还需要将客户端地址注册到服务端上，不然会出现服务端拒绝响应客户端的登录请求，服务注册的方式多样可以参照[官方手册](https://apereo.github.io/cas/5.1.x/index.html)，我这里使用JSON Service Registry 。需要修改**cas.properties**文件，添加如下内容：  
`cas.serviceRegistry.config.location: file:/etc/cas/services`  
同时还要在/etc/cas/services目录下创建json文件，添加如下内容：  
`{
  "@class" : "org.apereo.cas.services.RegexRegisteredService",
  "serviceId" : "http://cas.server.name.*",
  "name" : "cas.server.name",
  "id" : 1000000000000000001,
  "evaluationOrder" : 10
}`  
`http://cas.server.name为cas客户端的地址前缀`  
同时修改application.yml文件，添加如下内容：  
`cas:
  authn:
    accept:
      users: admin::admin`这一步是配置静态的登录用户名和密码  
修改完成后使用copy 命令就可以将工程目录下的etc/cas/目录拷贝到/etc/cas/这个目录，最后需要在工程目录下的pom.xml中添加解析json服务注册的jar包，内容如下：  
`<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-json-service-registry</artifactId>
    <version>${cas.version}</version>
</dependency>`  
然后重新打包，启动服务即可。  
---
**配置https证书**    
第一步，生成证书`keytool -genkey -alias casserver -keypass xxx -keyalg RSA -keysize 2048 -validity 3650 -keystore etc/cas/thekeystore -storepass xxx`  
第二步，导出证书`keytool -export -file etc/cas/casserver.crt -alias casserver -keystore etc/cas/thekeystore`  
第三步，把证书导入到客户端JDK中`keytool -import -keystore /var/lib/jdk1.8/jre/lib/security/cacerts -file etc/cas/casserver.crt -alias casserver` jdk证书cacerts的默认密码是changeit
* **部署 CAS Client**  
部署过程[参考文档](https://github.com/apereo/java-cas-client)
