商品データの登録・検索・削除
=================================

Play Frameworkに触ってみて、RESTfulなAPIアプリケーションを作成ます。

開発環境
===========

- OS: CentOS 7.2

- データベース: MariaDB 5.5.50

- Java 1.8.0_101

- Play Framework 2.5.5

開発のためのデータバースのコンフィグはconf/application.confに置きます。

テストのためのデータバースのコンフィグはconf/application.test.confに置きます。

WindowsOSを使う場合、Vagrantを使って同じな開発環境を設定てきます。
```sh
vagrant plugin install vagrant-vbguest
vagrant up
```

プロジェクトを起動するために、プロジェクトのフォルーダにアクセスして下記のコマンドをお使いになってください。
```sh
./bin/activator
run
```

現在にある機能は設計資料に書かれます。ユーザ登録する機能はまだありませんから、一時にtest/test123というアカウントをお使いになってください。

以上