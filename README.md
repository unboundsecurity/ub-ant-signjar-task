# ub-ant-signjar-task

The `ub-ant-signjar-task` enables simple integration of Unbound EKM with the `signjar` provided by `ant`.

## Build
To build a jar file, run `ant`

## Installation
Store the `/dist/ub-sign-jar.jar` file in your `/ant/lib` directory.

## Usage
Execute `ant` with `build.xml`.
For example :

```xml
<?xml version="1.0" encoding="ISO-8859-1"?>
<project name="UbSignJar" basedir="." default="ubsign">
    <target name="ubsign">
        <taskdef name="UbSignJar" classname="com.unboundtech.UbSignJar"/>
		
        <UbSignJar		
			jar="test.jar"
			signedjar="test.jar"
			partition="part1"
			alias="key"			
			installationDir= "C:\Program Files\DyadicSec"/>		
    </target>
</project>
```
## EKM-specific Signjar Options

EKM-specific attributes are specified by the following new `signjar` options:

* `Partition`: The EKM Partition name (optional, default: name of partion in `slot 0`)
* `ProviderPath` : The Unbound Java Security Provider jar path (optional)
* `InstallationDir` : The EKM installation directoty (optional, default : / (linux) , C:\Program Files\DyadicSec (win))

For the standard `signjar` options, referr to  https://ant.apache.org/manual/Tasks/signjar.html
