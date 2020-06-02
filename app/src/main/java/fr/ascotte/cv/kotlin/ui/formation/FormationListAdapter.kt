package fr.ascotte.cv.kotlin.ui.formation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.ascotte.cv.kotlin.R
import fr.ascotte.cv.kotlin.data.`object`.Formation
import kotlinx.android.synthetic.main.formation_list_item.view.*

class FormationListAdapter(val delegate:Delegate, private val formations: List<Formation>) : RecyclerView.Adapter<FormationListAdapter.FormationViewHolder>() {


    interface Delegate{
        fun formationClicked(formation: Formation, index:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormationViewHolder {

        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.formation_list_item, parent, false)
        val holder = FormationViewHolder(rootView)
        return holder
    }

    fun onItemClickedAtIndex(index:Int){

        val item = formations[index]
        delegate.formationClicked(item, index)
    }

    override fun getItemCount(): Int {
        return formations.size
    }

    override fun onBindViewHolder(holder: FormationViewHolder, position: Int) {
        val item = formations[position]
        holder.fillItem(item)
    }

    inner class FormationViewHolder(val rootView:View) : RecyclerView.ViewHolder(rootView), View.OnClickListener{

        init {

            rootView.setOnClickListener(this)
        }

        fun fillItem(item : Formation){

            rootView.ui_lbl_date.text = item.date
            rootView.ui_lbl_name.text = item.name
            var town = ""
            if(item.town.isNotEmpty() && item.town.isNotBlank())
                town = "- ${item.town}"
            val estab = "${item.establishment} $town"
            rootView.ui_lbl_establishment.text = estab
            rootView.ui_lbl_description.text = item.description

            if(adapterPosition == 0)
                rootView.ui_line_top.visibility = View.GONE

            if(adapterPosition == formations.size - 1)
                rootView.ui_line_bottom.visibility = View.GONE
        }

        override fun onClick(v: View?) {

            if(v != null)
                onItemClickedAtIndex(adapterPosition)
        }
    }
}

