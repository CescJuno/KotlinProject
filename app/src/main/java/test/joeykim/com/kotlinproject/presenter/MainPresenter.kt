package test.joeykim.com.kotlinproject.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MainPresenter : AbstractPresenter<MainContract.View>(), MainContract.Presenter {

    override fun updateMessage() {
        view!!.showMessage("MainPresenter example")
    }

}