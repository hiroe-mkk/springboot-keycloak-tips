# Spring Boot で Keycloak を開発環境用 OIDC プロバイダーとして使用する方法を説明するサンプルコード

## 起動方法

Docker Compose を使って Spring Boot アプリケーションと Keycloak を起動します。

```bash
docker-compose up -d
```

## プロファイルの使用

このアプリケーションには、`dev`（開発）と `prod`（本番）の 2 つのプロファイルがあります。デフォルトでは `dev` プロファイルが適用されています。

- `dev`: ローカル開発用に設定されており、ローカルインスタンスの Keycloak を使用します。
- `prod`: 本番環境向けで、Yahoo! ID 連携を利用します。このプロファイルを使用する場合は、クライアント ID (`YAHOO_CLIENT_ID`) とクライアントシークレット (`YAHOO_CLIENT_SECRET`) を環境変数として設定してください。

### 環境変数の設定例

デフォルトでは `dev` プロファイルが適用されるため、特別な設定は必要ありません。
`prod` プロファイルに切り替えたい場合は、以下の手順で環境変数を設定してください。

**1. .env ファイルを作成する**

プロジェクトのルートディレクトリに `.env` ファイルを作成し、以下のように環境変数を設定します。

```env
SPRING_PROFILES_ACTIVE=prod
YAHOO_CLIENT_ID=<Yahoo! JAPAN のクライアントID>
YAHOO_CLIENT_SECRET=<Yahoo! JAPAN のクライアントシークレット>
```

- `SPRING_PROFILES_ACTIVE` には、本番用のプロファイル (`prod`) を指定します。
- `YAHOO_CLIENT_ID` と `YAHOO_CLIENT_SECRET` は、Yahoo! ID 連携を使用するために必要です。これらの値は、[Yahoo!デベロッパーネットワーク](https://developer.yahoo.co.jp/yconnect/v2/index.html) の手順に従ってアプリケーションを登録し、取得してください。

**2. Docker Compose で .env ファイルを読み込む**

Docker Compose で `.env` ファイルを読み込むためには、`compose.yml` ファイルの `app` サービスに `env_file` オプションを追加します。

```diff_yaml:yaml
services:
  app:
    image: openjdk:17-jdk-slim-bullseye
    ports:
      - 8080:8080
+   env_file:
+     - .env
    volumes:
      - ./:/app
    working_dir: /app
    command: ./gradlew bootRun

  keycloak:
    ...
```

これで `.env` ファイルに定義された環境変数が自動的に Docker コンテナに読み込まれます。

## アクセス方法

### Keycloak 管理コンソール

Keycloak 管理コンソールにアクセスするには、以下の URL にアクセスしてください。

- **URL**: [http://localhost:18080](http://localhost:18080)
- **管理者ユーザー**
  - **ユーザー名**: `admin`
  - **パスワード**: `admin`

※ ユーザーの反映に時間がかかる場合があります。

### Spring Boot アプリケーション

Spring Boot アプリケーションにアクセスするには、以下の URL にアクセスしてください。

- **URL**: [http://localhost:8080](http://localhost:8080)
- **Keycloak ログインユーザー**
  - **ユーザー名**: `user`
  - **パスワード**: `user`
