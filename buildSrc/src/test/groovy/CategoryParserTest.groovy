import spock.lang.Specification

class CategoryParserTest extends Specification {
    def constructor() {
        expect:
        new CategoryParser(input).includingKeys == expIncludingKeys
        new CategoryParser(input).excludingKeys == expExcludingKeys

        where:
        input              || expIncludingKeys     | expExcludingKeys
        'payment'          || ['payment']          | []
        'payment,video'    || ['payment', 'video'] | []
        'payment, video'   || ['payment', 'video'] | []
        'db-mock'          || ['db-mock']          | []
        ''                 || []                   | []
        null               || []                   | []
        ' '                || []                   | []
        'payment,,video'   || ['payment', 'video'] | []
        'payment,video,'   || ['payment', 'video'] | []
        '-payment'         || []                   | ['payment']
        '-db-mock'         || []                   | ['db-mock']
        '-payment, -video' || []                   | ['payment', 'video']
        'payment, -video'  || ['payment']          | ['video']
        '-payment, video'  || ['video']            | ['payment']
    }

    def constructor_unexpected_keys() {
        when:
        new CategoryParser(input)

        then:
        def e = thrown(RuntimeException)
        e.message == exp

        where:
        input                                            || exp
        'unexpected'                                     || 'no such test categories: [unexpected]'
        'payment, unexpected'                            || 'no such test categories: [unexpected]'
        'payment, unexpected-1, -db-mock, -unexpected-2' || 'no such test categories: [unexpected-1, unexpected-2]'
    }

    def includes() {
        expect:
        new CategoryParser(input).includes() == exp

        where:
        input                      || exp
        'payment, -video, db-mock' || ['category.Payment', 'category.DatabaseMock']
    }

    def excludes() {
        expect:
        new CategoryParser(input).excludes() == exp

        where:
        input                       || exp
        '-payment, video, -db-mock' || ['category.Payment', 'category.DatabaseMock']
    }
}
