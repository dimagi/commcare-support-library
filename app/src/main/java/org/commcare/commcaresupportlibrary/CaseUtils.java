package org.commcare.commcaresupportlibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by willpride on 3/27/18.
 */

public class CaseUtils {

    private static String getPackageExtension(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("support-library", MODE_PRIVATE);
        return prefs.getString("extension", null);
    }

    public static void setPackageExtension(Context context, String extension) {
        SharedPreferences.Editor editor = context.getSharedPreferences("support-library", MODE_PRIVATE).edit();
        editor.putString("extension", extension);
        editor.apply();
    }

    private static String getCaseDbBaseUri(Context context) {
        StringBuilder uriBuilder = new StringBuilder("content://");
        String packageName = Constants.BASE_PACKAGE_NAME;
        uriBuilder.append(packageName);
        String packageExtension  = getPackageExtension(context);
        if (packageExtension != null) {
            uriBuilder.append(".").append(packageExtension);
        }
        uriBuilder.append(".case").append("/casedb/");
        return uriBuilder.toString();
    }

    private static String getCaseDbListUri(Context context) {
        return getCaseDbBaseUri(context) + "case/";
    }

    private static String getCaseDbDataUri(Context context) {
        return getCaseDbBaseUri(context) + "data/";
    }

    private static String getCaseDbAttachmentUri(Context context) {
        return getCaseDbBaseUri(context) + "attachment/";
    }

    private static String getCaseDbIndexUri(Context context) {
        return getCaseDbBaseUri(context) + "index/";
    }

    public static String getCaseName(Context context, String caseId) {
        Cursor cursor = getCaseMetaData(context, caseId);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getString(2);
        }
        return null;
    }

    public static String getCaseProperty(Context context, String caseId, String caseProperty) {
        Cursor caseDataCursor = CaseUtils.getCaseDataCursor(context, caseId);
        if (caseDataCursor == null) {
            return null;
        }
        while (caseDataCursor.moveToNext()) {
            String dataKey = caseDataCursor.getString(2);
            if (caseProperty.equals(dataKey)) {
                return caseDataCursor.getString(3);
            }
        }
        return null;
    }

    public static List<String> getCaseIds(Context context) {
        ArrayList<String> accumulator = new ArrayList<>();
        Cursor cursor = CaseUtils.getCaseMetaData(context);
        if (cursor == null) {
            return null;
        }
        while (cursor.moveToNext()) {
            String caseId = cursor.getString(2);
            accumulator.add(caseId);
        }
        return accumulator;
    }

    public static Map<String, String> getCaseProperties(Context context, String caseId, ArrayList<String> caseProperties) {
        HashMap<String, String> propertyMap = new HashMap<>();
        Cursor caseDataCursor = CaseUtils.getCaseDataCursor(context, caseId);
        if (caseDataCursor == null) {
            return null;
        }
        while (caseDataCursor.moveToNext()) {
            String dataKey = caseDataCursor.getString(2);
            if (caseProperties.contains(dataKey)) {
                propertyMap.put(dataKey, caseDataCursor.getString(3));
            }
        }
        return propertyMap;
    }

    /**
     * Provides a listing of all cases in the system, along with their metadata.
     * Returns all of the named attributes for a case (case_type, date_opened, etc) in columns.

     content://org.commcare.dalvik.case/casedb/case
     content://org.commcare.dalvik.case/casedb/case/CASE_ID

     Response Content

     column	        required
     case_id	    yes
     case_type	    yes
     owner_id	    yes
     status	        yes
     case_name	    yes
     date_opened    yes
     last_modified
     * @param context
     * @param caseId If specified, return only the row for the case with this ID
     * @return A cursor over the meta data specified
     */
    public static Cursor getCaseMetaData(Context context, String caseId) {
        Uri tableUri = Uri.parse(getCaseDbListUri(context) + caseId);
        return context.getContentResolver().query(tableUri, null, null, null, null);
    }

    public static Cursor getCaseMetaData(Context context) {
        Uri tableUri = Uri.parse(getCaseDbListUri(context));
        return context.getContentResolver().query(tableUri, null, null, null, null);
    }

    /**
     * Returns the key/value pairs of all data for a specific case

     content://org.commcare.dalvik.case/casedb/data/CASE_ID

     Response Content

     column 	required
     case_id	yes
     datum_id	yes
     value
     * @param context
     * @return A cursor over the meta data specified
     */
    public static Cursor getCaseDataCursor(Context context, String caseId) {
        Uri caseDataUri = Uri.parse(getCaseDbDataUri(context) + caseId);
        return context.getContentResolver().query(caseDataUri, null, null, null, null);
    }

    /**
     * Returns all indices for a specific case

     content://org.commcare.dalvik.case/casedb/index/CASE_ID

     Response Content

     column	    required
     case_id	yes
     index_id	yes
     case_type	yes
     value	yes
     */
    public static Cursor getCaseIndexData(Context context, String caseId) {
        Uri tableUri = Uri.parse(getCaseDbIndexUri(context) + caseId);
        return context.getContentResolver().query(tableUri, null, null, null, null);
    }

    /**
     * Returns all case attachments (still in flux) associated with a specific case.

     Note that the value returned is the full body of the attachment,
     which means this API is only viable for communicating with attachments that
     are not particularly large, depending on the amount of memory on the device.

     */
    public static Cursor getCaseAttachmentData(Context context, String caseId) {
        Uri tableUri = Uri.parse(getCaseDbAttachmentUri(context) + caseId);
        return context.getContentResolver().query(tableUri, null, null, null, null);
    }
}
