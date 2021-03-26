package study.kstock.stockcore.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "StockMarket")
data class StockMarket(
    @Column(length = 100) private var marketName: String,
    @Column(length = 100) private var region: String,
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int = 0
}