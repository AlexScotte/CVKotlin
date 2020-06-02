package fr.ascotte.cv.kotlin.ui.experiences

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.Client
import fr.ascotte.cv.kotlin.data.`object`.Company
import kotlinx.android.synthetic.main.list_header.view.*
import kotlinx.android.synthetic.main.list_item.view.*


class ExpandableListAdapter (val delegate: Delegate, val context:Context, val listOfHeaderData: List<Company>, val listOfChildData: HashMap<Company, List<Client>>) : BaseExpandableListAdapter() {

    interface Delegate{
        fun experienceClicked(company: Company, client: Client)
    }

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

        val group = getGroup(groupPosition) as Company
        val item = getChild(groupPosition, childPosition) as Client
        val rootView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val viewHolder = ChildViewHolder(rootView)
        viewHolder.fillItem(group, item)

        rootView.setOnClickListener{v ->

            delegate.experienceClicked(group, item)
        }

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
        private val ui_subtitle_text = rootView.ui_subtitle_text

        fun fillGroup(company: Company){

            val title = "${company.job} @ ${company.name}"
            ui_title_text.text = title

            val subtitle = "${company.dateStart} - ${company.dateEnd}"
            ui_subtitle_text.text  = subtitle
        }
    }

    inner class ChildViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        private val ui_child_title = rootView.ui_child_title
        private val ui_child_subtitle = rootView.ui_child_subtitle
        private val ui_chipGroup = rootView.ui_chipGroup

        fun fillItem(company: Company, client: Client){

            val title = "${client.name}"
            ui_child_title.text = title

            var duration:String = ""
            if(!client.experience?.duration.isNullOrEmpty())
                duration = "(${client.experience?.duration})"

            val subtitle =  "${client.experience?.job} $duration"
            ui_child_subtitle.text = subtitle

            if(client.experience != null){

                for (comp in client.experience!!.skills.filter { s -> s.important == 1 }){

                    val chip = Chip(ui_chipGroup.context)
                    chip.text = comp.name
                    chip.isClickable = false
                    chip.isCheckable = false
                    ui_chipGroup.addView(chip)
                }
            }
        }
    }
}