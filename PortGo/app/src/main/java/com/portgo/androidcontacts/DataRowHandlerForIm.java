/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License
 */
package com.portgo.androidcontacts;

import android.content.ContentValues;
import android.content.Context;
import com.portgo.androidcontacts.ContactsContract.CommonDataKinds.Im;

/**
 * Handler for IM address data rows.
 */
public class DataRowHandlerForIm extends DataRowHandlerForCommonDataKind {

    public DataRowHandlerForIm(
            Context context, ContactsDatabaseHelper dbHelper, ContactAggregator aggregator) {
        super(context, dbHelper, aggregator, Im.CONTENT_ITEM_TYPE, Im.TYPE, Im.LABEL);
    }

    @Override
    public boolean containsSearchableColumns(ContentValues values) {
        return values.containsKey(Im.DATA);
    }

    @Override
    public void appendSearchableData(SearchIndexManager.IndexBuilder builder) {
        int protocol = builder.getInt(Im.PROTOCOL);
        String customProtocol = builder.getString(Im.CUSTOM_PROTOCOL);
        builder.appendContent(
                Im.getProtocolLabel(mContext.getResources(), protocol, customProtocol).toString());
        builder.appendContentFromColumn(Im.DATA, SearchIndexManager.IndexBuilder.SEPARATOR_SLASH);
    }
}
