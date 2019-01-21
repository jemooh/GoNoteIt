package com.skyz.noteit.ui.create

import android.content.Context
import com.skyz.noteit.R
import com.skyz.noteit.type.ReadAccess
import com.skyz.noteit.type.WriteAccess

fun getReadPermsExplanation(context: Context, readAccess: ReadAccess) : String {

    var access: String =  when(readAccess) {
        ReadAccess.PUBLIC -> context.resources.getString(R.string.perms_read_explanation_everyone)
        ReadAccess.VIA_LINK -> context.resources.getString(R.string.perms_read_via_link)
        else -> context.resources.getString(R.string.perms_read_explanation_owner)
    }

    return context.getString(R.string.perms_read_explanation, access)
}

fun getWritePermsExplanation(context: Context, writeAccess: WriteAccess) : String {

    var access: String =  when(writeAccess) {
        WriteAccess.EVERYONE -> context.resources.getString(R.string.perms_write_explanation_everyone)
        else -> context.resources.getString(R.string.perms_write_explanation_owner)
    }

    return context.getString(R.string.perms_write_explanation, access)
}