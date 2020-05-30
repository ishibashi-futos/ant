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

## 