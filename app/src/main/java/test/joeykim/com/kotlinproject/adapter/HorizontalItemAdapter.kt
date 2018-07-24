package test.joeykim.com.kotlinproject.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.horizon_item_list.view.*
import test.joeykim.com.kotlinproject.R


class HorizontalItemAdapterItemAdapter(val items: ArrayList<HashMap<String,String>>, val context: Context?, val idx:Int) : RecyclerView.Adapter<ViewHolder1>(){
    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        if(idx == 0){
            holder?.rv_title?.text = items.get(position).get("title")
            Picasso.get().load(items.get(position).get("link")).fit().centerCrop().into(holder?.rv_image)
        }else{
            holder?.rv_title?.text = items.get(position).get("b_nam")
            Picasso.get().load(items.get(position).get("imgUrl")).fit().centerCrop().into(holder?.rv_image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
        if(idx == 0)
            return ViewHolder1(LayoutInflater.from(context).inflate(R.layout.horizon_item_list, parent, false))
        else
            return ViewHolder1(LayoutInflater.from(context).inflate(R.layout.horizon_book_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }


}

class ViewHolder1(view: View) : RecyclerView.ViewHolder(view){
    val rv_title = view.rv_title
    val rv_image = view.rv_image

}