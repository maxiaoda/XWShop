# XW—Shop XW商铺
这是一个前后端分离的SpringBoot项目

## 自动化SpringBoot框架搭建
开始创建 [SpringBoot](https://start.spring.io) 项目框架  
在 `Dependencies` 选项中加入以下依赖：
1. MyBatis Framework
2. JDBC API
3. MySQL Driver
4. Spring Web

## 自动化生成DAO层代码
在pom.xml中导入mybatis-generator插件，并且需要在该插件中单独配置mysql依赖
```xml
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.4.0</version>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>8.0.19</version>
                    </dependency>
                </dependencies>
            </plugin>
```

## 自动化检查规范搭建
### 配置checkstyle代码规范检查工具
- 在pom.xml中导入checkstyle插件
```xml
            <plugin>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                <configLocation>${basedir}/checkstyle.xml</configLocation>
                    <includeTestSourceDirectory>true</includeTestSourceDirectory>
                    <enableRulesSummary>false</enableRulesSummary>
                    <excludes>**/generate/**</excludes>
                </configuration>
                <executions>
                    <execution>
                        <id>compile</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>check</goal>
                        </goals>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>com.puppycrawl.tools</groupId>
                        <artifactId>checkstyle</artifactId>
                        <version>8.29</version>
                    </dependency>
                </dependencies>
            </plugin>
```
- 在根目录创建checkstyle.xml配置文件
(过滤不需要检查的文件夹)：
```xml
<configuration>
	...
        	<excludes>**/generate/**</excludes>
</configuration>
```

### 配置spotBugs代码缺陷见车工具
- 在pom.xml中导入spotBugs插件
```xml
            <plugin>
                <groupId>com.github.spotbugs</groupId>
                <artifactId>spotbugs-maven-plugin</artifactId>
                <version>3.1.12.2</version>
                <dependencies>
                    <!-- overwrite dependency on spotBugs if you want to specify the version of spotBugs -->
                    <dependency>
                        <groupId>com.github.spotbugs</groupId>
                        <artifactId>spotbugs</artifactId>
                        <version>4.0.0</version>
                    </dependency>
                </dependencies>
                <executions>
                    <execution>
                        <id>spotBugs</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>check</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```
- 过滤不要检查的文件,创建一个ignore.xml文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<FindBugsFilter>
<Match>
    <Class name="~.*\.generate\..*"/>
</Match>
</FindBugsFilter>
```
- 在pom.xml的spotBugs插件中添加
```xml
<configuration>
        ...
         <excludeFilterFile>ignore.xml</excludeFilterFile>
</configuration>
```