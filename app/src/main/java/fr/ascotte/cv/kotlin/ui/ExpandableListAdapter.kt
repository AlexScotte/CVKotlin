package fr.ascotte.cv.kotlin.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.Client
import fr.ascotte.cv.kotlin.data.`object`.Company
import kotlinx.android.synthetic.main.list_header.view.*
import kotlinx.android.synthetic.main.list_item.view.*


class ExpandableListAdapter (val context:Context, val listOfHeaderData: List<Company>, val listOfChildData: HashMap<Company, List<Client>>) : BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {

        return listOfHeaderData[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {

        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }


    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val company = getGroup(groupPosition) as Company
        val rootView = LayoutInflater.from(context).inflate(R.layout.list_header, parent, false)
        val viewHolder = HeaderViewHolder(rootView)
        viewHolder.fillGroup(company)

        return rootView
    }

    override fun getChildrenCount(groupPosition: Int): Int {

        return listOfChildData[listOfHeaderData[groupPosition]]!!.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return listOfChildData[listOfHeaderData[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val headerTitle = getChild(groupPosition, childPosition) as Client
        val rootView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val viewHolder = ChildViewHolder(rootView)
        viewHolder.fillItem(headerTitle)
        return rootView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {

        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {

        return listOfHeaderData.size
    }

    inner class HeaderViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        private val ui_title_text = rootView.ui_title_text

        init{

            //rootView.setOnClickListener(this)
        }

        fun fillGroup(company: Company){

            ui_title_text.text = company.name
        }
    }

    inner class ChildViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView), View.OnClickListener {

        private val ui_child_title = rootView.ui_child_title

        init{

            rootView.setOnClickListener(this)
        }

        fun fillItem(client: Client){

            ui_child_title.text = client.name
        }

        override fun onClick(v: View?) {
            // if(v != null)
            // onItemClickedAtIndex(adapterPosition)
        }
    }
}