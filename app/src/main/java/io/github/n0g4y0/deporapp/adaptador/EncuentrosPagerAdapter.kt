package io.github.n0g4y0.deporapp.adaptador

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.n0g4y0.deporapp.ui.encuentro.ListaConcluidosFragment
import io.github.n0g4y0.deporapp.ui.encuentro.ListaJuegosFragment
import io.github.n0g4y0.deporapp.ui.encuentro.ListaPendientesFragment

class EncuentrosPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        private const val ARG_OBJECT = "object"
    }


    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when(position + 1 ){

            1 -> { ListaPendientesFragment()}
            2 -> { ListaConcluidosFragment()}

            else -> ListaConcluidosFragment()
       }

     /*
        val fragment = ListaPendientesFragment()
        fragment.arguments = Bundle().apply {
        putInt(ARG_OBJECT, position + 1)
        }
        return fragment

    */

    }

}