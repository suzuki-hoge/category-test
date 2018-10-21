package service.common

import category.Common
import org.junit.experimental.categories.Category
import spock.lang.Specification

@Category(Common)
class CommonServiceTest extends Specification {
    def test() {
        expect:
        println 'out: service common'
        true
    }
}
