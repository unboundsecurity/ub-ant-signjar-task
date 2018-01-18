# ub-ant-signjar-task
## Build
ant
## installation
put /dist/ub-sign-jar.jar file in your /ant/lib directory
## usage
execute ant with build.xml, For exmaple :

```<?xml version="1.0" encoding="ISO-8859-1"?>
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
## options
* Partition: The EKM Partition name (optional, default : Slot 0)
* ProviderPath : The unbound Java Security Provider jar path (optional)
* InstallationDir : The EKM instaltion dir (optional, default : / (linux) , C:\Program Files\DyadicSec (win))
* Addinional options taken from https://ant.apache.org/manual/Tasks/signjar.html
