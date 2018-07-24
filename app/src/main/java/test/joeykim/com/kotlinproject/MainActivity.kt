package test.joeykim.com.kotlinproject

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import test.joeykim.com.kotlinproject.activity.WebViewActivity
import test.joeykim.com.kotlinproject.databinding.ActivityMainBinding
import test.joeykim.com.kotlinproject.fragment.BasePresenterFragment
import test.joeykim.com.kotlinproject.fragment.FirstFragment
import test.joeykim.com.kotlinproject.fragment.SecondFragment
import java.util.*

/**
 * Created by joey.j.kim on 2018. 3. 12..
 */

class MainActivity : FragmentActivity(), View.OnClickListener {
    var firstFragment:FirstFragment? = null
    var secondFragment:SecondFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Action Bar Hidden
        //supportActionBar?.hide()
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnOne.setOnClickListener(this)
        binding.btnTwo.setOnClickListener(this)
        binding.btnThree.setOnClickListener(this)
        //val user = User("kuma", 23)
        //binding.setVariable(BR.user,user)
        binding.executePendingBindings()

        //selectedTab(0)
    }

    override fun onClick(v: View?) {
        if(v?.equals(btn_one)!!){
            selectedTab(0)
        }else if(v?.equals(btn_two)!!){
            selectedTab(1)
            /*
            val nextIntent = Intent(this, WebViewActivity::class.java)
            nextIntent.putExtra("url", "https://m.daum.net")
            startActivity(nextIntent)
            */
        }else if(v?.equals(btn_three)!!){
            selectedTab(0)
        }
    }
    fun selectedTab(idx:Int){
        val fragment = getFragment(idx)
        replaceFragment(fragment, R.id.detail_fragment)
    }
    fun getFragment(idx: Int) : Fragment{

        if(idx == 0){
            if(firstFragment == null)
                firstFragment = FirstFragment()

            return firstFragment as FirstFragment
        }else{
            if(secondFragment == null)
                secondFragment = SecondFragment()

            return secondFragment as SecondFragment
        }

    }
    fun FragmentActivity.addFragment(fragment: Fragment, frameId: Int){
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    fun FragmentActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction{replace(frameId, fragment)}
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

}