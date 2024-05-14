package com.example.userssp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.userssp.databinding.ItemUserBinding

class UserAdapter(private val users: MutableList<User>, private val listener: OnClickListener) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemUserBinding.bind(view)

        fun setListener(user: User, position: Int){
            binding.root.setOnClickListener { listener.onClick(user, position) }
        }
    }

    //Bindea el UserAdapter.kt con el item_user.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    //Retorna la longitud de la lista a iterar
    override fun getItemCount(): Int {

        return users.size
    }

    //Hace la modificacion del xml (Por cada elemento) antes de renderizar
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("POSITION", position.toString())
        val user = users[position]
        val humanPosition = position + 1
        with(holder){
            setListener(user, humanPosition)
            binding.tvOrder.text = (humanPosition).toString()
            binding.tvName.text = user.getFullName()
            Glide.with(context)
                .load(user.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.imgPhoto)
        }
    }

    fun remove(position: Int) {
        users.removeAt(position)
        notifyItemRemoved(position)
    }
}