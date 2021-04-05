package study.kstock.stockcore.model

import javax.persistence.*

@Entity
@Table(name = "StockSymbol")
data class StockSymbol(
    @Column(length = 10) var symbol: String,
    @Column(length = 100) var name: String,

    @ManyToOne
    @JoinColumn(name = "marketId")
    var stockMarket: StockMarket,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int = 0
}