package org.commcare.commcaresupportlibrary;

/**
 * Created by willpride on 3/27/18.
 */

public class Constants {

    public static final String packageName = "org.commcare.dalvik";

    public static final String SESSION_ACTION = packageName + ".action.CommCareSession";

    public static final String CASE_DB_BASE_URI = "content://" + packageName + "/casedb/";
    public static final String CASE_DB_LIST_URI = CASE_DB_BASE_URI + "case/";
    public static final String CASE_DB_SINGLE_URI = CASE_DB_BASE_URI + "data/";
    public static final String CASE_DB_ATTACHMENT_URI = CASE_DB_BASE_URI + "attachment/";
    public static final String CASE_DB_INDEX_URI = CASE_DB_BASE_URI + "index/";

    public static final String FIXTURE_DB_BASE_URI = "content://" + packageName + ".fixture/fixturedb/";

    public static final String SESSION_REQUEST_KEY = "ccodk_session_request";
}
