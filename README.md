# Learn kotlin....

## 使いたいSpringBootバージョンに沿ってKotlinバージョンを決める。

<img width="800px" src="https://github.com/user-attachments/assets/cd6abfe0-8ce1-4349-bab8-f1c5b6627557" />

### 各バージョンを定義する。
- `build.gradle`に言語/LIB/コンパイル時のJVMバージョンを定義
- SpringBootバージョンに沿った`gradlew`ファイルをインストール
#### まず、SpringBootバージョン
```:.gradle
// 新しいプラグイン記法/推奨： pluginのバージョンを明示的に指定できる
plugins {
	// SpringBoot関連ライブラリのバージョン指定不要となる。
	// bootRun, bootJar, bootWarタスクが追加される。
	id 'org.springframework.boot' version '3.2.4'
```
#### SpringBootと互換性のあるKotlinバージョンを設定
```:.gradle
plugins {
	// Kotlin/Javaをコンパイルするためのプラグイン(JVMターゲットversion指定!)
	id 'org.jetbrains.kotlin.jvm' version "${kotlinVersion}"
	// Kotlin特有のデータクラスや拡張関数を Springのコンポーネントとして識別させる。
	// KotlinとSpring Bootの統合するときに よしなに依存性解決
	id 'org.jetbrains.kotlin.plugin.spring' version "${kotlinVersion}"
```

#### SpringBootと互換性のあるJava / Javaコンパイル後のJVMバージョンを指定
```:.gradle
// javaブロック指定 -> プロジェクト全体に対する設定（すべてのJavaコンパイルタスクに対して一括で適用）
java {
    sourceCompatibility = JavaVersion.VERSION_17  // Java 17 でコンパイル
    targetCompatibility = JavaVersion.VERSION_17  // Java 17 の JVM で動作するバイトコードを生成
}

// tasks.named, tasks,withType指定 -> 特定のGradleタスクに対して設定
tasks.named("compileJava", JavaCompile) {
    sourceCompatibility = '17'  // Java 17 でコンパイル
    targetCompatibility = '17'  // Java 17 の JVM で動作するバイトコードを生成
}
```

#### SpringBootと互換性のあるKotlinコンパイル後のJVMバージョンを指定
```:.gradle
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17" // Java 17 の JVM で動作するバイトコードを生成
}
```

#### SpringBootと互換性のあるGradleバージョンを指定
Gradleラッパーファイル(`gradlew`)を直接インストールするとき。

## KotlinをGradleで扱う
- 2024/06/22時点で、最新バージョンは2.0.0 ([what is the current version of Kotlin?](https://kotlinlang.org/docs/faq.html#what-is-the-current-version-of-kotlin))
- 標準ライブラリ(kotlin-stdlib)は1.4.0から[自動で追加されるので定義不要](https://kotlinlang.org/docs/whatsnew14.html#dependency-on-the-standard-library-added-by-default)。
- KotlinではJVMバージョンを自由に指定可能👍（[Kotlin lets you choose the version of JVM for execution.](https://kotlinlang.org/docs/faq.html#which-versions-of-jvm-does-kotlin-target)）
- 何も指定しないとJava8 compatibleなバイトコードを出力する👍（[By default, the Kotlin/JVM compiler produces Java 8 compatible bytecode](https://kotlinlang.org/docs/faq.html#which-versions-of-jvm-does-kotlin-targe)）
- 明示的に指定したい場合は、JavaとKotlinの両方のJVMコンパイルに設定をする必要がある👍
- KotlinコードをJVMターゲットにコンパイルするGradleタスクをインストールするためには`org.jetbrains.kotlin.jvm`プラグインをインストールする。
  - プラグインをインストールすると、Gradleタスク(`compileJava`/`compileTestJava`/`compileKotlin`/`compileTestKotlin`)が追加される。（`gradlew tasks --all`コマンドの`Other Tasks`に追加される）
  - **Gradleタスク名(`compileKotlin`)に、Javaクラス(`KotlinCompile.java`)が紐づいている。**
  - そう、KotlinはもちろんJavaもコンパイル対象となる。
  - Gradleのカスタムタスクで上記タスクをまとめている。 
    - `JavaCompile` -> `compileJava` ＋ `compileTestJava`
    - `KotlinCompile` -> `compileKotlin` ＋ `compileTestKotlin`
- 以下、KotlinとJavaを1.8でコンパイルされるよう明示的に指定する例
```gradle
plugins {
    id "org.jetbrains.kotlin.jvm" version "2.0.0"
}

// Javaファイルのコンパイル
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
// tasks.withType(JavaCompile)  でも指定可能👍

// Kotlinファイルのコンパイル
tasks.withType(KotlinCompile) {
	kotlinOptions {
        jvmTarget = "1.8"
	}
}
```

## `build.gradle`の定義方法
- plugins
- apply plugin:
- buildscript


## How to run on local

```
// 1. DB起動
docker compose up -d
// ※DBログイン
psql -p 5435 -U postgres -d kotlinDb
// 2. アプリ起動（FlywayでDB定義マイグレーション）
gradlew bootRun
// 3. テストデータ投入
psql -p 5435 -U postgres -d kotlinDb -f ./local/import-data.sql
```

## MyBatis
- [MyBatis-Spring-Boot-Starter](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)を利用する。
- `DataSource`,`SqlSessionFactory`周りのboilertemplateを書かずに済む！
- 用意するのは Mapper(XML), Mapper(Java/IF) の２つ
- Mapper(XML)は `src/main/resouces`配下に定義する。
- 通常なら`mybatis-config.xml`にて、MapperXMLの場所を１つ１つ定義する必要があるが、mybatis-spring-boot-starterを使えば`@MapperScan`で**MapperXMLが配置されているクラスパス**(**src/main/resouces/XXX**)を指定してスキャンすればOK👍
- 🔴XMLファイルでの実装 と 🔵アノテーションでの実装 の２つある。

### MapperXMLを自動スキャン

MapperIFをDIコンテナに登録する by `@MapperScan`<br>
→ ✅**MyBatisが自動的にIFの実装を提供してくれる。**
```kt
@MapperScan("jp.ats.kotlinlearning.repository")
@SpringBootApplication
class KotlinLearningApplication
```

### マッピングするクラスは空コンストラクタを用意すること -> [参考](https://qiita.com/5zm/items/0864d6641c65f976d415#17-%E3%83%9E%E3%83%83%E3%83%94%E3%83%B3%E3%82%B0%E3%81%99%E3%82%8B%E3%82%AF%E3%83%A9%E3%82%B9%E3%81%AB%E3%81%AF%E7%A9%BA%E3%81%AE%E3%82%B3%E3%83%B3%E3%82%B9%E3%83%88%E3%83%A9%E3%82%AF%E3%82%BF%E3%81%8C%E5%AD%98%E5%9C%A8%E3%81%99%E3%82%8B%E3%81%93%E3%81%A8)
```kt
data class EventWithParticipants(
    val id: Long, // BigSerialだからLong
    val eventName: String,
    val startsAt: LocalDateTime?,
    val endsAt: LocalDateTime?,
    val organizer: Organizer?,
    val participants: List<Participant>
) {
    // MyBatisは空のコンストラクタを利用してインスタンス生成するので定義必須
    constructor() : this(
        1L,
        "",
        null,null,null,
        // mutableなListでないとエラーとなる
        mutableListOf()
    )
}
```
#### [@NoArgsConstructorプラグイン](https://kotlinlang.org/docs/no-arg-plugin.html#command-line-compiler)で空コンストラクタを自動生成
```gradle
# build.gradle
noArg {
	annotation("jp.ats.kotlinlearning.annotation.NoArgsConstructor")
}
```
```kt
package jp.ats.kotlinlearning.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class NoArgsConstructor
```
↓これで、上記で定義したような無引数コンストラクタを自動生成する。
```kt
@NoArgsConstructor
data class EventWithParticipants(
    val id: Long,
    val eventName: String,
    val startsAt: LocalDateTime?,
    val endsAt: LocalDateTime?,
    val organizer: Organizer?,
    val participants: List<Participant>
)
```


### TypeHandler
- Java型 ⇔ DB型　のマッピング/変換処理を担う。
- 基本の型については、ライブラリ側で標準TypeHandlerが用意されている👍
- PostgreSQLの`jsonb`のようなDB型 ⇔ Javaクラス のマッピングをしたい場合、カスタムTypeHandlerを作成してあげる必要がある。

### カスタムTypeHandlerの作成方法
- [参考doc](https://mybatis.org/mybatis-3/ja/configuration.html#typeHandlers)
- BaseTypeHandlerクラスのサブクラスを作成する。
- 複数のクラスを扱う汎用的なTypeHandlerを作成する場合、**引数にClassを受け取るコンストラクタを定義**しておくと、MyBatisがインスタンス生成時に実際のクラスを渡してくれる。
```kt
@MappedJdbcTypes(JdbcType.JAVA_OBJECT)
class JSONBTypeHandler<E>(
    private val type: Class<E>
): BaseTypeHandler<E>
```
```xml
// javaType -> Javaクラス指定、typeHandler -> カスタムTypeHandler指定
<result column="eventDetails" property="eventDetails"
        javaType="jp.ats.kotlinlearning.model.EventDetails"
        typeHandler="jp.ats.kotlinlearning.repository.JSONBTypeHandler" />
```


# DbSetup
https://dbsetup.ninja-squad.com/approach.html

## data class



## 便利な標準組み込み関数
- takeIf

# 勉強フロー

- Controller - Validation - Service - Repository
- CRUD API をつくる<br>
  ・R: fetchPolicies -> map 使って変換, filter 使って抽出<br>
  ・C: ID 自動生成 -> Transaction<br>
  ・<br>
- Filter をつくる
- JWT 認証をつくる
- セッションを作る
- ModelMapper で DTO ⇔ Entity の変換を行う。

## Gradle

### build.gradle の書き方
