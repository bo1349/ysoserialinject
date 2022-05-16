## ysoserialinject

本项目用于生成注入冰蝎3专用内存马的payload，用法于ysoserial基本相同。可以被整合到各种Java反序列化利用工具中

本项目可以用于tomcat7，8，9的注入，以及spring MVC

目前支持的利用链如下：

CommonsBeanutils1Behinder

CommonsCollections2Behinder

CommonsCollections3Behinder

CommonsCollections4Behinder

Hibernate1Behinder

JBossInterceptors1Behinder

JSON1Behinder

Jdk7u21Behinder

MozillaRhino2Behinder

Spring1Behinder

Spring2Behinder

目前只支持冰蝎3.0，注入后的路径为/xyz，密码为rebeyond。如果需要更改，请自行修改ysoserial\payloads\TomcatFilterBehinder.java相关位置

## Building

```mvn clean package -DskipTests```

## 生成payload

```
java -jar ysoserialinject-0.0.1-SNAPSHOT-all.jar CommonsBeanutils1Behinder
```

## 使用举例

以一个测试用的反序列化环境为例

![1](.\img\1.png)

生成冰蝎3内存马注入payload

![2](.\img\2.png)

注入

![3](.\img\3.png)

测试成功

![4](.\img\4.png)

## 未来升级

1，加入regeorg隧道内存马注入方式

2，加入哥斯拉内存马注入方式
