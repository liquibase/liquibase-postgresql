# Liquibase Postgresql Extension [![Build](https://github.com/liquibase/liquibase-postgresql/actions/workflows/maven.yml/badge.svg)](https://github.com/liquibase/liquibase-postgresql/actions/workflows/maven.yml)

## Usage

The vacuum extension adds an additional changelog tag/command to support vacuuming.  As of version 1.0, it only supports database-level vacuuming.  Future versions will include table-level config as well as support for controlling vacuum options.  Note: vacuum cannot run in a transaction, so specify runInTransaction="false" in the containing changeSet.

There is currently no dedicated .xsd file file those using an XML-based change log.  Therefore, use the standard extension xsd of http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd.

## Available Commands/Tags

`<vacuum>`

Vacuums PostgreSQL database.

## Available attributes

None

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