package com.enjoyhac.booklist.screens.login


enum class LoadingState(val status: Status, val message: String? = null) {

//    companion object {
//        val IDLE = LoadingState(Status.IDLE)
//        val SUCCESS = LoadingState(Status.SUCCESS)
//        val LOADING = LoadingState(Status.LOADING)
//        val FAILED = LoadingState(Status.FAILED)
//    }
    IDLE(Status.IDLE),
    SUCCESS(Status.SUCCESS),
    LOADING(Status.LOADING),
    FAILED(Status.FAILED);

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        LOADING,
        IDLE,
    }
}
