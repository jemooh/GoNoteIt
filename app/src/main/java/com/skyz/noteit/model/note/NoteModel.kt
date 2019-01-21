package com.skyz.noteit.model.note

import com.skyz.noteit.dao.NoteEntity
import com.skyz.noteit.type.ReadAccess
import com.skyz.noteit.type.WriteAccess

open class NoteModel (){
    var uuid: String? = null
    var title: String? = null
    var content: String? = null
    var id: Long? = null
    var imageBase64: String? = null
    var updatedAt: Long? = null
    var readAccess = ReadAccess.PRIVATE
    var writeAccess = WriteAccess.ONLY_OWNER


    constructor(noteEntity: NoteEntity): this() {
        uuid = noteEntity.uuid
        title = noteEntity.title
        content = noteEntity.content
        id = noteEntity.id
        imageBase64 = noteEntity.imageBase64
        updatedAt = noteEntity.updatedAt
        readAccess = noteEntity.readAccess
        writeAccess = noteEntity.writeAccess
    }

    override fun equals(obj: Any?): Boolean {
        return obj is NoteModel &&
                uuid == obj.uuid &&
                id == obj.id &&
                areStringsEqual(title, obj.title) &&
                areStringsEqual(content, obj.content) &&
                areStringsEqual(imageBase64, obj.imageBase64)
    }

    private fun areStringsEqual(s: String?, s2: String?): Boolean {
        return s == null && s2 == null || s == s2
    }
}
