# category-test
sample for categorized test on spock.

### create annotation classes for each category name
```groovy
@interface Payment { }

@interface Video { }

@interface DatabaseMock { }
```

### annotate `@Category` with category name annotation classes
```groovy
import org.junit.experimental.categories.Category

@Category(Payment)
class PaymentServiceTest extends Specification { ... }
```

### set include and exclude on build.gradle
```groovy
tasks.withType(Test) {
    CategoryParser cp = new CategoryParser(System.getProperty('categories'))

    useJUnit {
        includeCategories cp.includes()
        excludeCategories cp.excludes()
    }
}
```

### run gradle test task with category names
```
$ ./gradlew test --rerun-tasks -Dcategories=payment,-db-mock
```

### reference: parser in sample code, and parser test
#### parser
```groovy
class CategoryParser {
    private final List<String> includingKeys
    private final List<String> excludingKeys

    CategoryParser(String input) {
        if ((input ?: '').allWhitespace) {
            includingKeys = []
            excludingKeys = []
        } else {
            List<String> keys = input.split(',').collect { it.trim() }.findAll { !it.allWhitespace }

            includingKeys = keys.findAll { !it.startsWith('-') }
            excludingKeys = keys.findAll { it.startsWith('-') }.collect { it.replaceFirst('-', '') }
        }

        List<String> unexpectedKeys = (includingKeys + excludingKeys).findAll { !definitions.containsKey(it) }
        if (!unexpectedKeys.empty) throw new RuntimeException("no such test categories: $unexpectedKeys")
    }

    private static final Map<String, String> definitions = [
            'common' : 'category.Common',
            'payment': 'category.Payment',
            'video'  : 'category.Video',
            'db-mock': 'category.DatabaseMock'
    ]

    String[] includes() {
        includingKeys.collect { definitions.get(it) }.toArray()
    }

    String[] excludes() {
        excludingKeys.collect { definitions.get(it) }.toArray()
    }
}
```

#### test
```groovy
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
```

