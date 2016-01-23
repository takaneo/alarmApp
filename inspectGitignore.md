# いま管理対象のファイルを後から管理対象外にする
　
### 実行手順
すでに管理対象となっているファイルは、.gitignoreに記述しただけでは対象から外れない。
下記のようなコマンドライン操作が必要となる。

```
ファイルの場合
$ git rm --cached mfp.cache.js

フォルダの場合 (-r を忘れずに)
$ git rm --cached -r cache/
```

なお、下記のように一旦全てを対象から外してから元に戻すという`強引な`方法なら、
個別に除外ファイルを指定する必要なし。

```
全てを対象から外す
$ git rm --cached -r .

全てを対象に含める (.gitignore に記述されたものは除く)
$ git add -A .
```
　
### 確認方法
除外するパターンは記述したものの、実際のところどれが除外されているのかを確認したいとき。

```
$ git ls-files --others --ignored --exclude-standard

上のコマンドの短縮形
$ git ls-files -oi --exclude-standard
```
