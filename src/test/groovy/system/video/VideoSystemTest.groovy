package system.video

import category.DatabaseMock
import category.Video
import org.junit.experimental.categories.Category
import spock.lang.Specification

@Category([Video, DatabaseMock])
class VideoSystemTest extends Specification {
    def test() {
        expect:
        println 'out: system video,   system db-mock'
        true
    }
}
