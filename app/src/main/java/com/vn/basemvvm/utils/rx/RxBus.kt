

package com.vn.basemvvm.utils.rx

import android.os.Handler
import android.os.Looper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlin.reflect.KClass

object RxBus {

    const val STATUS_CODE = "STATUS_CODE"
    const val NOTIFICATION = "NOTIFICATION"

    private val mHandler = Handler(Looper.getMainLooper())
    private val map = HashMap<String,  Disposable>()
    private val subject: PublishSubject<Any> by lazy {
        val bs = PublishSubject.create<Any>()
        bs.observeOn(AndroidSchedulers.mainThread())
        bs
    }

    fun push(data: Any) {
        subject.onNext(data)
    }

    // Chỉ dành cho đăng ký một nơi
    // khi đăng ký nơi khác cùng clazz thì cũ sẽ bị huỷ
    fun <T: Any> subscribe(clazz: KClass<T>, callback: ((T) -> Unit)? = null) {
        unregister(clazz.java)
        map[clazz.java.simpleName] = subject.ofType(clazz.java).subscribe{onNext ->
            callback?.invoke(onNext)
        }
    }

    // Đăng ký nhiều lần với tên khác nhau
    fun <T: Any> subscribe(name: String, clazz: KClass<T> , callback: ((T) -> Unit)? = null) {
        unregister(name)
        val dispose = subject.ofType(clazz.java).subscribe { onNext ->
            mHandler.post {  callback?.invoke(onNext) }
        }
        map[name] = dispose
    }

    fun unregister(clazz: Class<*>) {
        map.remove(clazz.simpleName)?.dispose()
    }

    fun unregister(name: String) {
        map.remove(name)?.dispose()
    }

}
