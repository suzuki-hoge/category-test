package service.video

import category.Video
import org.junit.experimental.categories.Category
import spock.lang.Specification

@Category(Video)
class VideoServiceTest extends Specification {
    def test() {
        expect:
        println 'out: service video'
        true
    }
}
