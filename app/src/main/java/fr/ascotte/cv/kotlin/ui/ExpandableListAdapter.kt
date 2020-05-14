package fr.ascotte.cv.kotlin.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import fr.ascotte.cv.kotlin.R
import kotlinx.android.synthetic.main.list_header.view.*
import kotlinx.android.synthetic.main.list_item.view.*


class ExpandableListAdapter (val context:Context, val listOfHeaderData: List<String>, val listOfChildData: HashMap<String, List<String>>) : BaseExpandableListAdapter() {

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

        val headerTitle = getGroup(groupPosition) as String
        val rootView = LayoutInflater.from(context).inflate(R.layout.list_header, parent, false)
        val viewHolder = HeaderViewHolder(rootView)
        viewHolder.fillItem(headerTitle)

        // Expand groupview at the beginning
        val mExpandableListView = parent as ExpandableListView
        mExpandableListView.expandGroup(groupPosition)
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
        val headerTitle = getChild(groupPosition, childPosition) as String
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

        fun fillItem(title:String){

            ui_title_text.text = title
        }

//        override fun onClick(v: View?) {
//            super.onClick
//        }

//        override fun onClick(v: View?) {
//           // if(v != null)
//               // onItemClickedAtIndex(adapterPosition)
//        }
    }

    inner class ChildViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView), View.OnClickListener {

        private val ui_child_title = rootView.ui_child_title

        init{

            rootView.setOnClickListener(this)
        }

        fun fillItem(title:String){

            ui_child_title.text = title
        }

        override fun onClick(v: View?) {
            // if(v != null)
            // onItemClickedAtIndex(adapterPosition)
        }
    }
}