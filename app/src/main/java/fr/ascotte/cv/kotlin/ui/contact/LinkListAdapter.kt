package fr.ascotte.cv.kotlin.ui.contact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.ExternalLink
import fr.ascotte.cv.kotlin.data.`object`.Formation
import kotlinx.android.synthetic.main.activity_experience_details.*
import kotlinx.android.synthetic.main.formation_list_item.view.*
import kotlinx.android.synthetic.main.link_list_item.view.*

class LinkListAdapter(val context:Context, val delegate:Delegate, private val links: List<ExternalLink>) : RecyclerView.Adapter<LinkListAdapter.LinkViewHolder>() {


    interface Delegate{
        fun linkClicked(link: ExternalLink, index:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {

        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.link_list_item, parent, false)
        val holder = LinkViewHolder(rootView)
        return holder
    }

    fun onItemClickedAtIndex(index:Int){

        val item = links[index]
        delegate.linkClicked(item, index)
    }

    override fun getItemCount(): Int {
        return links.size
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        val item = links[position]
        holder.fillItem(item)
    }

    inner class LinkViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView), View.OnClickListener{

        init {

            rootView.setOnClickListener(this)
        }

        fun fillItem(item : ExternalLink){

            var url = item.url.toLowerCase()
            url = url.replace("https://", "")
            url = url.replace("http://", "")
            url = url.replace("www.", "")

            rootView.ui_link_name.text = url

            if(item.imageUrl.isNotEmpty() && item.imageUrl.isNotBlank())
                Glide.with(context).load(item.imageUrl).override(80, 80).into(rootView.ui_img_link)
        }

        override fun onClick(v: View?) {

            if(v != null)
                onItemClickedAtIndex(adapterPosition)
        }

        private fun removeChar(original:String, str:String) : String{

            var newString = original
            if(original.contains(str)){
                newString = original.replace(str, "")
            }
            return newString
        }
    }
}