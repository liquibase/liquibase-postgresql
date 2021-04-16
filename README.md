# Liquibase Postgresql Extension [![Build](https://github.com/liquibase/liquibase-postgresql/actions/workflows/build.yml/badge.svg)](https://github.com/liquibase/liquibase-postgresql/actions/workflows/build.yml)

The vacuum extension adds an additional changelog tag/command to support vacuuming. As of version 1.0, it only supports database-level vacuuming.  Future versions will include table-level config as well as support for controlling vacuum options.  

**Note:** vacuum cannot run in a transaction, so specify `runInTransaction="false"` in the containing changeset.

There is currently no dedicated .xsd file using an XML-based changelog. Therefore, use the standard extension xsd of http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd.

## Available Commands/Tags

`<vacuum>`

Vacuums PostgreSQL database.

## Configuring the extension

These instructions will help you get the extension up and running on your local machine for development and testing purposes. This extension has a prerequisite of Liquibase core in order to use it. Liquibase core can be found at https://www.liquibase.org/download.

### Liquibase CLI

Download [the latest released Liquibase extension](https://github.com/liquibase/liquibase-postgresql/issues) `.jar` file and place it in the `liquibase/lib` install directory. If you want to use another location, specify the extension `.jar` file in the `classpath` of your [liquibase.properties file](https://docs.liquibase.com/workflows/liquibase-community/creating-config-properties.html).

### Maven
Specify the Liquibase extension in the `<dependency>` section of your POM file by adding the `org.liquibase.ext` dependency for the Liquibase plugin. 
 
```  
<plugin>
     <!--start with basic information to get Liquibase plugin:
     include <groupId>, <artifactID>, and <version> elements-->
     <groupId>org.liquibase</groupId>
     <artifactId>liquibase-maven-plugin</artifactId>
     <version>4.3.2</version>
     <configuration>
        <!--set values for Liquibase properties and settings
        for example, the location of a properties file to use-->
        <propertyFile>liquibase.properties</propertyFile>
     </configuration>
     <dependencies>
     <!--set up any dependencies for Liquibase to function in your
     environment for example, a database-specific plugin-->
            <dependency>
                 <groupId>org.liquibase.ext</groupId>
                 <artifactId>liquibase-postgresql</artifactId>
                 <version>${liquibase-postgresql.version}</version>
            </dependency>
         </dependencies>
      </plugin>
  ``` 

## Example

```xml
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd
        http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
    <changeSet id="1" author="nvoxland" runInTransaction="false">
        <ext:vacuum/>
    </changeSet>
</databaseChangeLog>
```

## Contribution

To file a bug, improve documentation, or contribute code, follow our [guidelines for contributing](https://www.liquibase.org/community). 

[This step-by-step instructions](https://www.liquibase.org/community/contribute/code) will help you contribute code for the extension. 

Once you have created a PR for this extension you can find the artifact for your build using the following link: [https://github.com/liquibase/liquibase-postgresql/actions/workflows/build.yml](https://github.com/liquibase/liquibase-postgresql/actions/workflows/build.yml).

## Documentation

[Using Liquibase with PostgreSQL on Windows](https://docs.liquibase.com/workflows/database-setup-tutorials/postgresql.html)

## Issue Tracking

Any issues can be logged in the [Github issue tracker](https://github.com/liquibase/liquibase-postgresql/issues).

## License

This project is licensed under the [Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.html).
