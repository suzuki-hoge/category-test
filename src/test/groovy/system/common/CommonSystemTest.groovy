package system.common

import category.Common
import org.junit.experimental.categories.Category
import spock.lang.Specification

@Category(Common)
class CommonSystemTest extends Specification {
    def test() {
        expect:
        println 'out: system common'
        true
    }
}
