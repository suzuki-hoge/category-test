package service.payment

import category.Payment
import org.junit.experimental.categories.Category
import spock.lang.Specification

@Category(Payment)
class PaymentServiceTest extends Specification {
    def test() {
        expect:
        println 'out: service payment'
        true
    }
}
