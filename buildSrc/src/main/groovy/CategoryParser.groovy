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
