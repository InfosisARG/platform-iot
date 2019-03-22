# platform-iot

This is an Application based on Plugin Framework for Java (PF4J) with an embedded ActiveMQ.

https://github.com/pf4j/pf4j
With PF4J you can easily transform a monolithic java application in a modular application.
PF4J is an open source (Apache license) lightweight (around 50 KB) plugin framework for java, with minimal dependencies (only slf4j-api) and very extensible (see PluginDescriptorFinder and ExtensionFinder).

https://github.com/apache/activemq
Apache ActiveMQ is an open source message broker written in Java together with a full Java Message Service (JMS) client.
With ActiveMQ you can make the plugins communicate with each other.

A plugin is a way for a third party to extend the functionality of an application. A plugin implements extension points declared by application or other plugins. Also a plugin can define extension points.


## How to use

Plugin configuration is read from path /plugin-example/src/main/resources/plugin.properties
```properties
plugin.id=
plugin.class=
plugin.description=
plugin.version=1.0.0
plugin.provider=Infosis
plugin.dependencies=
platform.receiveTopics=
platform.sendTopic=
```
### Plugin's home
It's possible to define a custom folder for the plugin's home. So, in this case it's necessary to add a context-param in your web.xml with param name "*pf4j.pluginsDir*". If that value is not defined, the plugins home will be created on the same directory of the executable file. For example, if you are using Apache Tomcat as application server, the plugins home will be created inside of the bin folder.

```xml

<context-param>
    <param-name>pf4j.pluginsDir</param-name>
    <param-value>/path/to/plugins</param-value>
</context-param>
	
```
