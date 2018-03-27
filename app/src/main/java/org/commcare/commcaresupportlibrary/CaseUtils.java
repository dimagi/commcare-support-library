package org.commcare.commcaresupportlibrary;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import static org.commcare.commcaresupportlibrary.Constants.CASE_DB_ATTACHMENT_URI;
import static org.commcare.commcaresupportlibrary.Constants.CASE_DB_INDEX_URI;
import static org.commcare.commcaresupportlibrary.Constants.CASE_DB_LIST_URI;
import static org.commcare.commcaresupportlibrary.Constants.CASE_DB_SINGLE_URI;

/**
 * Created by willpride on 3/27/18.
 */

public class CaseUtils {

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
        Uri tableUri = Uri.parse(CASE_DB_LIST_URI + caseId);
        return context.getContentResolver().query(tableUri, null, null, null, null);
    }

    public static Cursor getCaseMetaData(Context context) {
        Uri tableUri = Uri.parse(CASE_DB_LIST_URI);
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
        Uri caseDataUri = Uri.parse(CASE_DB_SINGLE_URI + "data/" + caseId);
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
        Uri tableUri = Uri.parse(CASE_DB_INDEX_URI + caseId);
        return context.getContentResolver().query(tableUri, null, null, null, null);
    }

    /**
     * Returns all case attachments (still in flux) associated with a specific case.

     Note that the value returned is the full body of the attachment,
     which means this API is only viable for communicating with attachments that
     are not particularly large, depending on the amount of memory on the device.

     */
    public static Cursor getCaseAttachmentData(Context context, String caseId) {
        Uri tableUri = Uri.parse(CASE_DB_ATTACHMENT_URI + caseId);
        return context.getContentResolver().query(tableUri, null, null, null, null);
    }
}
