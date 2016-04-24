package com.xplenty.api.model;

import com.xplenty.api.Xplenty;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

/**
 * Author: Xardas
 * Date: 24.04.16
 * Time: 11:47
 */
public class SearchQueryTest extends TestCase {

    @Test
    public void testBuilder() {
        Date now = new Date();
        SearchQuery sq = createMockSearchQuery(now);
        assertFalse(sq.isEmpty());
        assertEquals(String.format("attr1:>%s attr2:<666 attr3:omg attr4:\"supercomplex search string\" -attr5:cookies",
            SearchQuery.getDateFormat().format(now)), sq.toString());
    }

    public static SearchQuery createMockSearchQuery(Date now) {
        SearchQuery sq = new SearchQuery();
        assertTrue(sq.isEmpty());
        sq.addDatePredicate("attr1", Xplenty.SearchOperation.greater_or_equal, now);
        sq.addNumericPredicate("attr2", Xplenty.SearchOperation.lesser_or_equal, 666);
        sq.addStringPredicate("attr3", "omg");
        sq.addStringPredicate("attr4", "supercomplex search string");
        sq.addStringPredicate("attr5", "cookies", true);
        return sq;
    }
}
