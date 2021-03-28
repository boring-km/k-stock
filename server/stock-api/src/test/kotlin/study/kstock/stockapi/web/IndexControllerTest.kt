package study.kstock.stockapi.web

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndexControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @DisplayName("메인 페이지 로딩")
    @Test
    internal fun loadMainPage() {
        // when
        val body = this.restTemplate.getForObject("/", String::class.java)

        // then
        assertThat(body).contains("STOCK API 서버")
    }
}