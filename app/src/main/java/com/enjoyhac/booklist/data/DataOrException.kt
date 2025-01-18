package com.enjoyhac.booklist.data

// データ取得や処理中に発生する 状態（データ、読み込み中フラグ、エラー）を一つのオブジェクトで管理するためのもの
data class DataOrException<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
)
