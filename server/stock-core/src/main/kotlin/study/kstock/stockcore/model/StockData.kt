package study.kstock.stockcore.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "StockData")
class StockData(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int,

    @ManyToOne
    @JoinColumn(name = "symbolId")
    var stockSymbol: StockSymbol,

    @Column var lastPrice: Double,
    @Column var priceChange: Double,
    @Column var percentChange: Double): Serializable
