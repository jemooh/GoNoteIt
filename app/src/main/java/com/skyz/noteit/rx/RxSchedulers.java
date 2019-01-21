package com.skyz.noteit.rx;

import io.reactivex.Scheduler;

public interface RxSchedulers {

    Scheduler io();

    Scheduler androidMainThread();
}
