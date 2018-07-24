package test.joeykim.com.kotlinproject.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import test.joeykim.com.kotlinproject.presenter.BasePresenter
import test.joeykim.com.kotlinproject.presenter.BaseView

abstract class BasePresenterFragment<in VIEW : BaseView, P : BasePresenter<VIEW>> : BaseFragment() {

    protected var presenter: P? = null

    abstract fun onCreatePresenter(): P?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = onCreatePresenter()
        presenter?.attachView(this as VIEW)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView()
    }
}