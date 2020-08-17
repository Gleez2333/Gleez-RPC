## Gleez-RPC

一个简单RPC框架的实现。Gleez-RPC是一款基于Netty+Zookeeper+Nacos实现的RPC框架。由于本人能力有限，实现了简单的RPC框架功能。



### 运行项目

1. 需下载zookeeper或nacos并启动

2. 服务端启动：添加@ServiceScan注解和@RegistryConfig注解，并在RegistryConfig中配置注册中心和地址，如下：

   ```java
   @RegistryConfig(type = RegistryType.ZOOKEEPER, address = "127.0.0.1:2181")
   @ServiceScan
   public class TestNettyServer {
       public static void main(String[] args) {
           RpcServer server = new NettyServer("127.0.0.1", 9998, new ProtobufSerializer());
           server.start();
       }
   }
   
   或
       
   @ServiceScan
   @RegistryConfig(type = RegistryType.NACOS, address = "127.0.0.1:8848")
   public class TestSocketServer {
       public static void main(String[] args) {
           SocketServer socketServer = new SocketServer("127.0.0.1", 9999);
           socketServer.start();
       }
   }
   ```

3. 客户端启动：添加@RegistryConfig注解，并在RegistryConfig中配置注册中心和地址，如下：

   ```java
   @RegistryConfig(type = RegistryType.ZOOKEEPER, address = "127.0.0.1:2181")
   public class TestNettyClient {
       public static void main(String[] args) {
           RpcClient client = new NettyClient(new ProtobufSerializer(), new RandomLoadBalance());
           RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
           HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
           ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
           for(int i=1;i<=10;++i) {
               System.out.println(helloService.hello("第 " + i + " : 个消息"));
           }
           for (int i = 1; i <= 10; i++) {
               System.out.println(byeService.bye(" " + i));
           }
   
       }
   }
   
   或
       
   @RegistryConfig(type = RegistryType.NACOS, address = "127.0.0.1:8848")
   public class TestSocketClient {
       public static void main(String[] args) {
   
           SocketClient client = new SocketClient();
           RpcClientProxy proxy = new RpcClientProxy(client);
           HelloService helloService = proxy.getProxy(HelloService.class);
           String res = helloService.hello("hello, my RPC-Framework");
           System.out.println(res);
       }
   }
   ```

   

