package study.kstock.stockcore.model

import javax.persistence.*

@Entity
@Table(name = "StockSymbol")
data class StockSymbol(
    @Column(length = 10) private var symbol: String,
    @Column(length = 100) private var name: String,

    @ManyToOne
    @JoinColumn(name = "marketId")
    private var stockMarket: StockMarket,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int = 0
}