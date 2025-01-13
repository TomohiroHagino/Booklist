package com.enjoyhac.booklist.model

data class MUser(
    val id: String?,
    val userId: String, // ユーザーID
    val displayName: String, // 表示名
    val avatarUrl: String, // アバターURL（任意）
    val quote: String, // 引用（任意）
    val profession: String // 職業（任意）
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "quote" to this.quote,
            "profession" to this.profession,
            "avatar_url" to this.avatarUrl
        )
    }
}
