package study.kstock.stockcore.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "StockData")
data class StockData(
    @ManyToOne
    @JoinColumn(name = "symbolId")
    private var stockSymbol: StockSymbol,

    @Column private var lastPrice: Double,
    @Column private var priceChange: Double,
    @Column private var percentChange: Double,
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int = 0

    fun getLastPrice(): Double {
        return lastPrice
    }
}
