package system

import category.DatabaseMock
import org.junit.experimental.categories.Category
import spock.lang.Specification

@Category(DatabaseMock)
abstract class DatabaseMockSpecification extends Specification {}
