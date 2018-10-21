package api.video

import category.Video
import org.junit.experimental.categories.Category
import spock.lang.Specification

@Category(Video)
class VideoApiTest extends Specification {
    def test() {
        expect:
        println 'out: api video'
        true
    }
}
