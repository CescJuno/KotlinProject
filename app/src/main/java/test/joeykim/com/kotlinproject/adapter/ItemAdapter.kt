package test.joeykim.com.kotlinproject.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_list.view.*
import test.joeykim.com.kotlinproject.R
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.SnapHelper



class ItemAdapter(val items: ArrayList<Any>?, val context: Context?) : RecyclerView.Adapter<ViewHolder>(){


    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var itemListDataAdapter = HorizontalItemAdapterItemAdapter(items?.get(position) as ArrayList<HashMap<String, String>>, context, position);



        holder?.recycler_view_list?.setHasFixedSize(true)
        holder?.recycler_view_list?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder?.recycler_view_list?.adapter = itemListDataAdapter
        if(position == 0){
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(holder?.recycler_view_list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }

}

class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
    val recycler_view_list = view.recycler_view_list
}