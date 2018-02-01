# backlogのWebhookをSlackに通知するAWS Lambda用プログラム

## デプロイ方法

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

- デプロイ
```
> ./gradlew migrateFunction
```

- 設定
    - slackのTOKENは環境変数の「SLACKTOKEN」から取得する
    - 投稿するチャンネルはリクエストパラメータの`channel`から取得する
    - 投稿者名は環境変数の「BOTNAME」から取得する

- その他
