# Java Embed Tool

[![Build Status](https://travis-ci.org/binarybabel/java-embed-tool.svg?branch=master)](https://travis-ci.org/binarybabel/java-embed-tool)

`embed-tool` is designed to embed Java source-code in another project's namespace. Information on its use, as well as a deployment pattern using Git Submodules and Gradle is available below. This repository contains the source for the command-line executable. 

* Common use case is creating single-jar plugins/addons for other pieces of software.
* Removes the need for external runtime dependencies on other JARs.
* Prevents version conflicts between components requiring a different version of the same depencency.

Please be sure you are respecting the licenses of any source-code you attempt to embed in your project. There are legitimate use-cases for this method, and the deployment pattern below makes every effort maintain the chain of attribution for decendent projects. In using this tool you are responsible for all liabilities resulting in the final product.


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
