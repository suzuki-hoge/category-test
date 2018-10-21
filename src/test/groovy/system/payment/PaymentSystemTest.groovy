package system.payment

import category.DatabaseMock
import category.Payment
import org.junit.experimental.categories.Category
import spock.lang.Specification

@Category([Payment, DatabaseMock])
class PaymentSystemTest extends Specification {
    def test() {
        expect:
        println 'out: system payment, system db-mock'
        true
    }
}
