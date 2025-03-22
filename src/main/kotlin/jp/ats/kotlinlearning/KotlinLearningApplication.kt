package jp.ats.kotlinlearning

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// マッパーXMLの場所を設定ファイルに定義しない代わりに、@MapperScanでスキャンして自動取得する。
// ↓で指定しているのは、クラスパス(src/main/resources)配下のXMLファイル。
@MapperScan(basePackages = ["jp.ats.kotlinlearning.repository"])
@SpringBootApplication
class KotlinLearningApplication

fun main(args: Array<String>) {
	runApplication<KotlinLearningApplication>(*args)
}
