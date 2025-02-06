# io.github.openhelios.epever.tracer

Unofficial Java library to communicate with EPEVER Tracer 3210AN.

## Required Access Rights

### Linux

Start `AddGroupOnLinux.sh` to add your user to the user group
needed to get access to the serial device.

### Windows

Install device driver.

## Build Library

### Build with Maven

1. Install `mvn`, if not already installed.
2. Execute

```
mvn install
```

### Build with Gradle

1. Install `gradle`, if not already installed.
2. Execute

```
gradle build
```

## Run Example

Plug-in USB cable.

### Linux

Execute

```
cd build/distributions
unzip io.github.openhelios.epever.tracer-0.0.1.zip
./io.github.openhelios.epever.tracer-0.0.1/bin/io.github.openhelios.epever.tracer
```

### Windows

* Extract ZIP file `io.github.openhelios.epever.tracer-0.0.1.zip` from
  sub folder `build/distributions`.
* Start extracted file
  `io.github.openhelios.epever.tracer-0.0.1/bin/io.github.openhelios.epever.tracer.bat`

## Usage of Example

Type h to get a list of possible functions provided by the example
application.

## Usage of Library

Choose one of the following Maven, Gradle or manual dependency.

### Add Maven Dependency

Add the following Maven dependency to your pom.xml file:

```
<dependency>
	<groupId>io.github.openhelios</groupId>
	<artifactId>epever.tracer</artifactId>
	<version>0.0.1</version>
</dependency>
```

### Add Gradle Dependency

Add the following Gradle dependency to your build.gradle file:

```
implementation 'io.github.openhelios:epever.tracer:0.0.1'
```

### Download Dependency Manually

Download the [JAR file](https://repo1.maven.org/maven2/io/github/openhelios/epever.tracer/0.0.1/epever.tracer-0.0.1.jar) manually, if you are using no build tool.

### Usage Example

See usage example in [Main.java](src/main/java/io/github/openhelios/fnirsi/dps/Main.java).


## Links

* Official manual: [https://www.epever.com/download/tracer-an-series-1040a/](https://www.epever.com/download/tracer-an-series-1040a/)
* Protocol: [ControllerProtocolV2.3.pdf](https://github.com/kasbert/epsolar-tracer/blob/master/archive/ControllerProtocolV2.3.pdf)

Inspired by
* [gotracer](https://github.com/spagettikod/gotracer)
* [epsolar-tracer](https://github.com/kasbert/epsolar-tracer)
* [Tracer-RS485-Modbus-Blynk-V2](https://github.com/tekk/Tracer-RS485-Modbus-Blynk-V2)

