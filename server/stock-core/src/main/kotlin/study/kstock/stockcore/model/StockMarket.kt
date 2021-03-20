package study.kstock.stockcore.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "StockMarket")
class StockMarket(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int,

    @Column(length = 100) var marketName: String,
    @Column(length = 100) var region: String): Serializable