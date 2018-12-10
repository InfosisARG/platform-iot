# platform-iot

## How to use

### Plugin's home
It's possible to define a custom folder for the plugin's home. So, in this case it's necessary to add a context-param in your web.xml with param name "*pf4j.pluginsDir*". If that value is not defined, the plugins home will be created on the same directory of the executable file. For example, if you are using Apache Tomcat as application server, the plugins home will be created inside of the bin folder.

```xml

<context-param>
    <param-name>pf4j.pluginsDir</param-name>
    <param-value>/path/to/plugins</param-value>
</context-param>
	
```