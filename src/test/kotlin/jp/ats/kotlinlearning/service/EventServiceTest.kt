package jp.ats.kotlinlearning.service

import jp.ats.kotlinlearning.model.Event
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.sql.DataSource
import com.ninja_squad.dbsetup_kotlin.dbSetup
import com.ninja_squad.dbsetup_kotlin.mappedValues
import org.springframework.core.io.ClassPathResource
import org.springframework.util.FileCopyUtils

@SpringBootTest // アプリ起動→DB接続→flyway実行 まで行う。
class EventServiceTest {

    // ServiceクラスをAutowiredする
    @Autowired
    lateinit var service: EventService

    // DataSourceIFの実装クラスをよしなにインスタンス化してDIしてくれる
    @Autowired
    lateinit var dataSource: DataSource

    @BeforeEach
    fun setup() {
        // sequence初期化用SQL
        val initSeqSql: String = ClassPathResource("sql/initialize-sequences.sql").inputStream.use {
            FileCopyUtils.copyToString(it.bufferedReader())
        }

        dbSetup(to = dataSource) {
            // sequence初期化 by SQL
            sql(initSeqSql)
            // DELETE FROM 各テーブル
            deleteAllFrom(
                "rel_event_participant",
                "participant",
                "event",
                "organizer"
            )
            // データ投入
            insertInto("organizer") {
                mappedValues(
                    "organization_name" to "組織1"
                )
            }
            insertInto("event") {
                mappedValues(
                    "organizer_id" to 1,
                    "event_name" to "イベント1",
                    "starts_at" to "2024-05-02 10:30:00",
                    "ends_at" to "2024-05-02 12:00:00"
                )
            }
        }.launch()

    }

    @Test
    fun findAllEvents() {
        val results: List<Event> = service.findEvents()
        assertThat(results.size).isEqualTo(1)
    }

}