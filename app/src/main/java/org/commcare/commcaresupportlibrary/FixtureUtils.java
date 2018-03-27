package org.commcare.commcaresupportlibrary;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import static org.commcare.commcaresupportlibrary.Constants.FIXTURE_DB_BASE_URI;

/**
 * Created by willpride on 3/27/18.
 */

public class FixtureUtils {

    public static Cursor getFixtureList(Context context) {
        Uri tableUri = Uri.parse(FIXTURE_DB_BASE_URI);
        return context.getContentResolver().query(tableUri, null, null, null, null);
    }

    public static Cursor getFixtureData(Context context, String fixtureId) {
        Uri tableUri = Uri.parse(FIXTURE_DB_BASE_URI + fixtureId);
        return context.getContentResolver().query(tableUri, null, null, null, null);
    }
}
