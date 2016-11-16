# Java Embed Tool

[![Build Status](https://travis-ci.org/binarybabel/java-embed-tool.svg?branch=master)](https://travis-ci.org/binarybabel/java-embed-tool)

`embed-tool` is designed to embed Java source-code in another project's namespace. **[Compiled releases are available on GitHub](https://github.com/binarybabel/java-embed-tool/releases).**

Instructions and a deployment pattern using [Git Submodules](https://git-scm.com/book/en/v2/Git-Tools-Submodules) and [Gradle](https://gradle.org/) are available below. This repository contains the source for the command-line executable.

* Common use case is in creating single-jar plugins/addons for other pieces of software.
* Removes the need for external runtime dependencies on other JARs.
* Prevents version conflicts between components requiring a different version of the same dependency.

Please be sure you are respecting the licenses of any source-code you embed in a project. There are legitimate use-cases for this method, and the deployment pattern below makes every effort maintain the chain of attribution for descendent projects. In using this tool you are responsible for all liabilities resulting in the final product.

## Getting Started

These instructions assume the use of Gradle and a `src/main/java` base directory for your project's code.

#### Inputs

```
$PROJECT_ROOT = Base directory for your project, containing 'src/main/java'
$PROJECT_PACKAGE = Root package name for your project
$EMBED_REPO = URL of repo containing source-code to embed
$EMBED_NAME = Name/alias for embedded repo (letters & numbers only)
$EMBED_PACKAGE = Root package name of embedded code
```

#### Installing embed-tool

```
cd $PROJECT_ROOT
mkdir -p src/embed/java src/embed/repo
cd src/embed

# Download and extract embed-tool.jar and README.txt
https://github.com/binarybabel/java-embed-tool/releases
```

#### Update Gradle includes

You will need to modify your `build.gradle` to include the embedded code,
both for the compile and in the generated artifact.

```
sourceSets {
    embed
    main {
        compileClasspath += embed.output
        runtimeClasspath += embed.output
    }
    test {
        compileClasspath += embed.output
        runtimeClasspath += embed.output
    }
}

jar {
    from sourceSets.embed.output
    from sourceSets.main.output
}

javadoc {
    classpath = sourceSets.main.compileClasspath
}
```

## Using embed-tool

Add embedded code as a git submodule

```
cd $PROJECT_ROOT/src/embed
git submodule add $EMBED_REPO repo/$EMBED_NAME
```

Update `embed/README.txt` with the embed details. **Example:**

```
=========================================================================================
    LIBRARY                         AUTHORS
        VERSION                     PACKAGE
        LICENSE                     WEBSITE
=========================================================================================

    solid-tx                        BinaryBabel OSS
        0.0.1                       org.binbab.solidtx
        MIT                         https://github.com/binarybabel/solid-tx
```

Run `embed-tool ` to add the embed code to your project's namespace

```
./embed-tool.jar $EMBED_NAME $EMBED_PACKAGE $PROJECT_PACKAGE
```

The imported source code is now available as `$PROJECT_PACKAGE.embed.$EMBED_NAME` and the files
under `src/embed` should be committed to source-control. The original (unembedded) source for
each import will not be part of your project repo directly, but is referenceable as a git submodule.

To update the embedded code in the future, perform a `git pull` within the `src/embed/repo/$EMBED_NAME` directory and then re-run the `embed-tool`.

## Author and License

 - **Author:** BinaryBabel OSS (<oss@binarybabel.org>)
 - **License:** MIT

```
The MIT License (MIT)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
