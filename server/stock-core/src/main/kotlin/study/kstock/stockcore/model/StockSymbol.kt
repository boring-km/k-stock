package study.kstock.stockcore.model

import javax.persistence.*

@Entity
class StockSymbol(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int,

    @Column(length = 10) var symbol: String,
    @Column(length = 100) var name: String,

    @ManyToOne
    @JoinColumn(name = "marketId")
    var stockMarket: StockMarket

) {

    override fun toString(): String {
        return "StockSymbol(id=$id, symbol='$symbol', name='$name', stockMarket=$stockMarket)"
    }


}
