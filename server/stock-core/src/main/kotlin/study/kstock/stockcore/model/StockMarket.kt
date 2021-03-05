package study.kstock.stockcore.model

import org.springframework.data.redis.core.index.Indexed
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "StockMarket")
class StockMarket(id: Int, marketName: String, country: String): Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Indexed
    @Column(length = 100)
    var marketName: String

    @Column(length = 100)
    var country: String

    init {
        this.id = id
        this.marketName = marketName
        this.country = country
    }
}