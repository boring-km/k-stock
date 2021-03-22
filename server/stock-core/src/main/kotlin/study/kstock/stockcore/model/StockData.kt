package study.kstock.stockcore.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "StockData")
data class StockData(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int,

    @ManyToOne
    @JoinColumn(name = "symbolId")
    private var stockSymbol: StockSymbol,

    @Column private var lastPrice: Double,
    @Column private var priceChange: Double,
    @Column private var percentChange: Double): Serializable {

    override fun toString(): String {
        return "StockData(id=$id, stockSymbol=$stockSymbol, lastPrice=$lastPrice, priceChange=$priceChange, percentChange=$percentChange)"
    }
}
