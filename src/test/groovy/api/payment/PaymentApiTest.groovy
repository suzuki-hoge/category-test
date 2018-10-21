package api.payment

import category.Payment
import org.junit.experimental.categories.Category
import spock.lang.Specification

@Category(Payment)
class PaymentApiTest extends Specification {
    def test() {
        expect:
        println 'out: api payment'
        true
    }
}
