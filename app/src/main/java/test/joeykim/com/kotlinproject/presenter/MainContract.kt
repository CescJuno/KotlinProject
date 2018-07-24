package test.joeykim.com.kotlinproject.presenter

interface MainContract {

    interface View : BaseView {
        fun showMessage(message: String)

    }

    interface Presenter : BasePresenter<View> {

        fun updateMessage()
    }

}