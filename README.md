# Antでできることをまとめる

## 引数を取得する

### 概要

引数を、`-D${name}=${value}`形式で取得する
### サンプル

xml

```xml
<target name="argument">
  <echo>Show: env=${env}</echo>
</target>
```

コマンド・結果

```bash
$ ant argument -Denv=prod
Buildfile: build.xml

argument:
     [echo] Show: env=prod

BUILD SUCCESSFUL
Total time: 1 second
```

## ファイルを削除する

### 概要

`<delete>`タグを使用し、記述する

* dir ・・・ ディレクトリを削除する場合に使用する。
* file ・・・ 削除したいファイルを指定する。
* `<fileset>` ・・・ ファイルセットタグと、includesプロパティを使用し、正規表現を利用してファイル削除ができる.

### サンプル

xml

```xml
<target name="delete-sample">
  <delete dir="target/" />
  <delete file="logs/application.log.bak" />
  <delete>
    <fileset dir="." includes="logs/**/*.log" />
  </delete>
</target>
```

コマンド・結果

```bash
$ ant delete-sample
Buildfile: build.xml

argument:
     [echo] Show: env=prod

BUILD SUCCESSFUL
Total time: 1 second
```

## AntでJavaをコンパイルする

### 概要

`<javac>`タグを利用し、Javaソースコードをコンパイルする

* source/target ・・・ ソースコードのバージョン、ビルドターゲットを指定する
* debug ・・・ ソースをデバッグ情報付きでコンパイルするかどうかを指定します。
* includeantruntime ・・・ Antランタイムをクラスパスに追加するかどうか。なぜかデフォルトON・・・
* executable ・・・ ビルド実行するJDKのパス。この例では、環境変数に指定された`JAVA_HOME`のパスにあるjavacコマンドを読んでいる
* classpath ・・・ クラスパスを指定する。ワイルドカードが使えなかったので、`<classpath>`タグで指定してみた。
* srcdir/destdir ・・・ ビルド元のディレクトリ（src/main/java）と、結果の出力先（target/classes）。

### サンプル

xml

```xml
<target name="compile">
  <property environment="env"/>
  <javac srcdir="${src.dir}" destdir="${build.dir}"
      source="${src.version}"
      target="${src.target}"
      debug="on"
      includeantruntime="false"
      executable="${env.JAVA_HOME}/bin/javac"
  >
    <classpath>
      <fileset dir="lib/">
        <include name="*.jar" />
      </fileset>
    </classpath>
  </javac>
</target>
```

コマンド・結果

```bash
ant compile
Buildfile: /dir/antTest/build.xml

compile:

clean:
   [delete] Deleting directory /dir/antTest/target
    [mkdir] Created dir: /dir/antTest/target/classes
    [javac] Compiling 1 source file to /dir/antTest/target/classes

BUILD SUCCESSFUL
Total time: 2 seconds
```

## Jarを作成する

### 概要

`<jar>`タグを利用する。

* basedir/destfile ・・・ classが出力された元ディレクトリ/出力先ファイル名

### サンプル

xml

```xml
<target name="jar">
  <ant target="clean" />
  <ant target="compile" />
  <echo>jarファイルを作成します</echo>
  <jar basedir="${build.dir}" destfile="${build.lib}/${jar.name}.jar"/>
  <copy todir="${build.lib}/">
    <fileset dir="${lib.dir}">
      <include name="*.jar" />
    </fileset>
  </copy>
</target>
```

コマンド・実行結果

```bash
$ ant jar
Buildfile: /dir/antTest/build.xml

jar:

clean:
   [delete] Deleting directory /dir/antTest/target
    [mkdir] Created dir: /dir/antTest/target/classes

compile:

clean:
   [delete] Deleting directory /dir/antTest/target
    [mkdir] Created dir: /dir/antTest/target/classes
    [javac] Compiling 1 source file to /dir/antTest/target/classes
     [echo] jarファイルを作成します
      [jar] Building jar: /dir/antTest/target/lib/App.jar
```

## Java資産を実行する

### 概要

`<java>`タグを使用し、javaコマンドを実行する

* classname ・・・ メインクラス名を指定する
* maxmemory ・・・ -Xmxを指定する
* fork ・・・ antのJVMと異なるjvmとして起動する.
* jvm ・・・ jvmを起動するjavaコマンドのパスを指定する.

### サンプル

xml

```xml
<target name="run">
  <ant target="jar" />
  <java classname="${src.basePackage}.${src.main}"
    maxmemory="256m"
    fork="false"
    jvm="${env.JAVA_HOME_11}/bin/java"
    >
    <classpath>
      <fileset dir="target/lib/">
        <include name="*.jar" />
      </fileset>
    </classpath>
  </java>
</target>
```

コマンド・実行結果

```bash
$ ant run
Buildfile: /dir/antTest/build.xml

run:

jar:

clean:
   [delete] Deleting directory /dir/antTest/target
    [mkdir] Created dir: /dir/antTest/target/classes

compile:

clean:
   [delete] Deleting directory /dir/antTest/target
    [mkdir] Created dir: /dir/antTest/target/classes
    [javac] Compiling 1 source file to /dir/antTest/target/classes
     [echo] jarファイルを作成します
      [jar] Building jar: /dir/antTest/target/lib/App.jar
     [copy] Copying 1 file to /dir/antTest/target/lib
     [java] JVM args ignored when same JVM is used.
     [java] Hello, Ant.
     [java] * Java version : 25.252-b09
     [java] * OS name :Linux
     [java] * OS version :4.19.104-microsoft-standard
```