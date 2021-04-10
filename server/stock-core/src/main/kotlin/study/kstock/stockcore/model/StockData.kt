package study.kstock.stockcore.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "StockData")
data class StockData(
    @ManyToOne
    @JoinColumn(name = "symbolId")
    var stockSymbol: StockSymbol,

    @Column var lastPrice: Double,
    @Column var priceChange: Double,
    @Column var percentChange: Double,
) : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int = 0

}
