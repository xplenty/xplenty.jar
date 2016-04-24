package com.xplenty.api.model;

import com.xplenty.api.Xplenty;
import com.xplenty.api.request.AbstractSearchRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Class for convinient search query building
 *
 * Author: Xardas
 * Date: 24.04.16
 * Time: 11:22
 */
public class SearchQuery {
    public static final String CLUSTER_ID = "id";
    public static final String CLUSTER_NAME = "name";
    public static final String CLUSTER_NODES = "nodes";
    public static final String CLUSTER_CREATED_DATE = "created";
    public static final String CLUSTER_TERMINATED_DATE = "terminated";

    public static final String HOOK_ID = "id";
    public static final String HOOK_NAME = "name";
    public static final String HOOK_TYPE = "type";

    public static final String JOB_ID = "id";
    public static final String JOB_STARTED_DATE = "started";
    public static final String JOB_FINISHED_DATE = "finished";
    public static final String JOB_STATUS = "status";
    public static final String JOB_DURATION = "duration";
    /**
     * search in related packages by the name attribute
     */
    public static final String JOB_PACKAGE_NAME = "package";
    /**
     * search in related logs by the body attribute
     */
    public static final String JOB_LOG = "log";
    /**
     * search in related schedule names by the name attribute
     */
    public static final String JOB_SCHEDULE_NAME = "schedule";
    /**
     * search in related users (owners) by the name and email attributes
     */
    public static final String JOB_USER = "user";

    public static final String PUBLIC_KEY_ID = "id";
    public static final String PUBLIC_KEY_NAME = "name";

    public static final String PACKAGE_ID = "id";
    public static final String PACKAGE_NAME = "name";
    public static final String PACKAGE_DESCRIPTION = "description";
    public static final String PACKAGE_USER = "user";
    public static final String PACKAGE_CREATED_DATE = "created";
    public static final String PACKAGE_UPDATED_DATE = "modified";

    public static final String SCHEDULE_ID = "id";
    public static final String SCHEDULE_NAME = "name";
    public static final String SCHEDULE_DESCRIPTION = "description";
    public static final String SCHEDULE_STATUS = "status";
    /**
     * search in related packages by the name attribute
     */
    public static final String SCHEDULE_PACKAGE_NAME = "package";

    protected List<SearchPredicate> searchPredicates = new ArrayList<>();
    protected final Xplenty.SearchSort searchSort;
    protected final Xplenty.SortDirection sortDirection;
    private static ThreadLocal<DateFormat> df = new ThreadLocal<>();

    public SearchQuery() {
        searchSort = Xplenty.SearchSort.relevant;
        sortDirection = Xplenty.SortDirection.descending;
    }

    public SearchQuery(Xplenty.SearchSort searchSort, Xplenty.SortDirection sortDirection) {
        this.searchSort = searchSort;
        this.sortDirection = sortDirection;
    }

    public SearchQuery addDatePredicate(String searchAttribute, Xplenty.SearchOperation searchOp, Date searchDate) {
        searchPredicates.add(new SearchPredicate(searchAttribute, searchOp, getDateFormat().format(searchDate), false));
        return this;
    }

    public SearchQuery addStringPredicate(String searchAttribute, String searchValue) {
        return addStringPredicate(searchAttribute, searchValue, false);
    }

    public SearchQuery addStringPredicate(String searchAttribute, String searchValue, boolean negation) {
        searchPredicates.add(new SearchPredicate(searchAttribute, Xplenty.SearchOperation.equal, searchValue, negation));
        return this;
    }

    public SearchQuery addNumericPredicate(String searchAttribute, Xplenty.SearchOperation searchOp, Number searchValue) {
        searchPredicates.add(new SearchPredicate(searchAttribute, searchOp, searchValue.toString(), false));
        return this;
    }

    public boolean isEmpty() {
        return searchPredicates.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SearchPredicate searchPredicate : searchPredicates) {
            sb.append(searchPredicate.toString()).append(" ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public Properties toProperties() {
        Properties props = new Properties();
        props.put("q", toString());
        props.put(AbstractSearchRequest.PARAMETER_DIRECTION, sortDirection);
        props.put(AbstractSearchRequest.PARAMETER_SORT, searchSort);
        return props;
    }

    protected static DateFormat getDateFormat() {
        DateFormat dateFormat = df.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            df.set(dateFormat);
        }
        return dateFormat;
    }

    public static class SearchPredicate {
        protected final String searchAttribute;
        protected final Xplenty.SearchOperation searchOp;
        protected final String searchValue;
        protected final boolean negation;

        public SearchPredicate(String searchAttribute, Xplenty.SearchOperation searchOp, String searchValue, boolean negation) {
            this.searchAttribute = searchAttribute;
            this.searchOp = searchOp;
            this.searchValue = searchValue.contains(" ") ? String.format("\"%s\"", searchValue) :  searchValue;
            this.negation = negation;
        }

        @Override
        public String toString() {
            return String.format("%s%s%s%s", negation ? "-" : "", searchAttribute, searchOp.toString(), searchValue);
        }
    }
}
