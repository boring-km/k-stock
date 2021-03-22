package study.kstock.stockcore.model

import javax.persistence.*

@Entity
@Table(name = "StockSymbol")
data class StockSymbol(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int,

    @Column(length = 10) private var symbol: String,
    @Column(length = 100) private var name: String,

    @ManyToOne
    @JoinColumn(name = "marketId")
    private var stockMarket: StockMarket) {
    override fun toString(): String {
        return "StockSymbol(id=$id, symbol='$symbol', name='$name', stockMarket=$stockMarket)"
    }
}
