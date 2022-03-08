# Frege Gradle Plugin

![build](https://github.com/tricktron/frege-gradle-plugin/actions/workflows/build.yml/badge.svg)

Simplifies setting up your Frege project.

## Installation

You need `java >= 11` and `gradle >= 7`.

```bash
git clone https://github.com/tricktron/frege-gradle-plugin.git
./gradlew publishToMavenLocal
```

## How to Use
1. Specify the frege compiler release, version, main module and repl module 
file in your `build.gradle`:

```groovy
plugins
{
    id 'ch.fhnw.thga.frege' version '1.9.0-alpha'
}

frege 
{
    version    = '3.25.84'
    release    = '3.25alpha'
    mainModule = 'examples.HelloFrege' // see runFrege task
    replModule = 'examples.HelloFrege' // see replFrege task
}
```

Then run
```bash
gradle initFrege
gradle runFrege
```

See the [Frege Releases](https://github.com/Frege/frege/releases) for all available versions.

Optional configuration parameters inside `build.gradle`:
- compilerDownloadDir: defaults to `<projectRoot>/lib`
- mainSourceDir: defaults to `<projectRoot>/src/main/frege`
- outputDir: defaults to `<projectRoot>/build/classes/main/frege`
- compilerFlags: defaults to `['-O', '-make']`

### Added Tasks

- **setupFrege**: Downloads the specified version of the Frege compiler.
- **initFrege**: Creates a default `HelloFrege.fr` example file under
 `mainSource/examples/HelloFrege.fr`. Alternatively, you can specify the location
 on the command line with `--mainModule=my.mod.HelloFrege`.
- **compileFrege**: Compiles all your `*.fr` files in `mainSourceDir` to `outputDir`.
Alternatively, you can also pass the compile item by command line. Then only the
compile item and its dependencies get compiled. 
E.g.: `gradle compileFrege --compileItem=[full module name | absolute path to .fr file]`.
- **runFrege**: Runs the Frege module specified by `mainModule`. Alternatively you can
also pass the main module by command line, e.g: `gradle runFrege --mainModule=my.mod.Name`.
- **replFrege**: Takes care of all project dependencies of the specified `replModule`
and prints the command to start the Frege REPL and load the `replModule`. 
E.g.: `(echo :l <path to replModule.fr> && cat) | java -cp <your-correct-classpath-with-all-dependencies> frege.repl.FregeRepl`.
On Unix you can even further automate starting the repl and loading the module
 with the following one-liner:
`eval $(./gradlew -q replFrege)`.

### Dependencies

Dependencies can be configured as expected in your `build.gradle` file, using the
`implementation` scope, e.g.:

```groovy
repositories {
    # Add your Frege repo here
}

dependencies {
    implementation 'org.frege-lang:fregefx:0.8.2-SNAPSHOT'
}
```

### Build Cache

The `compileFrege` task supports incremental builds from build cache. Enable the build
cache by setting `org.gradle.caching=true` in your `gradle.properites`.


## How to Contribute
Try to add another task, e.g. `fregeDoc` to the [FregePluginFunctionalTest.java](src/functionalTest/java/ch/fhnw/thga/gradleplugins/FregePluginFunctionalTest.java) file and try to make the test pass.