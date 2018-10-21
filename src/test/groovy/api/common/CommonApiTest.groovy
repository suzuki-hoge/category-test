package api.common

import category.Common
import org.junit.experimental.categories.Category
import spock.lang.Specification

@Category(Common)
class CommonApiTest extends Specification {
    def test() {
        expect:
        println 'out: api common'
        true
    }
}
