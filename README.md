## backlogのWebhookをSlackに通知するAWS Lambda用プログラム

### 使い方
- 事前設定
    - `~/.aws/credentials` ファイルを作成し、マネジメントコンソールでIAMユーザーを作成したときに取得した `aws_access_key_id` と `aws_secret_access_key` を記載しておく。
    - `build.gradle` の`aws.profileName "default"` は、`credentials`ファイル内の [default] プロファイルを参照する。別のプロファイル名で別のアクセスキーを記述しておくことも可能。
```
[default]
aws_access_key_id = ABCDEFGHIJKLMNOPQRST
aws_secret_access_key = 40chars-SECRET-ACCESS-KEY-you-got-in-csv

[dev]
aws_access_key_id = A*******************
aws_secret_access_key = another-SECRET-ACCESS-KEY-you-got-in-csv
```

- SlackのBotAppの作成
    1. https://api.slack.com/apps から `Create New App` を選択
    2. 適当な `App Name` と 通知先のSlack Workspaceを選択する
    3. `Add features and functionality` - `Permissions` を選択し、`Scopes`で `Send messages as xxxx`を追加し`Save Changes`を押す
    4. `Install your app to your workspace` から `Install App to Workspace` を選択しInstallする
    5. `Add features and functionality` - `Permissions` を選択すると`OAuth Access Token`が表示されるので控えておく

- Lamda Functionのデプロイ
```
> ./gradlew migrateFunction -Pname="[任意の関数名]"
```

- Lamda Functionの設定
    - 環境変数に下記の3つを設定する
        - SLACKTOKEN : 上で取得した`OAuth Access Token`
        - BOTNAME : Slackに投稿する投稿者名
        - BASEURL : BacklogのURL（https://hogehoge.backlog.jp/ とか）

- API Gatewayの設定
    1. APIの作成を選択 (API名、説明は任意の値を)
    2. アクションで `リソースの作成` を選択 (リソース名、リソースパスは `backlog-webhook`)
    3. `backlog-webhook` で `リソースの作成` を選択 (リソース名、リソースパスは `{channel}`)
    4. `{channel}` で `メソッドの作成` を選択 `POST` で作成。Lamda関数にデプロイした際のLamda関数名を設定する
    5. `統合リクエスト` の `本文マッピングテンプレート` で `テンプレートが定義されていない場合 (推奨) ` を選択してマッピングテンプレートを追加する
        - Content-Type : application/json
        - テンプレート : mapping_template.txt
    6. アクションで `アプリケーションのデプロイを選択`
    
- Backlogの設定
    - `プロジェクト設定` - `Webhook` で `Webhookを追加する` を選択し、API GatewayのURLを設定する
        - URLの `{channel}` 部分は投稿したいSlackのチャンネルを指定する

