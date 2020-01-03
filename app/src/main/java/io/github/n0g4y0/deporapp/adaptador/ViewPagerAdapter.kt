package io.github.n0g4y0.deporapp.adaptador

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.ui.encuentro.EncuentrosPagerFragment
import io.github.n0g4y0.deporapp.ui.encuentro.ListaConcluidosFragment
import io.github.n0g4y0.deporapp.ui.encuentro.ListaPendientesFragment

/*
*
* adaptador para manejar el viewPager de encuentros deportivos:
* */

class ViewPagerAdapter(private val context: Context?,
                        fragmentManager: FragmentManager?): FragmentStatePagerAdapter(fragmentManager!!) {

    private val fragmentList = listOf(
        ListaPendientesFragment(),
        ListaConcluidosFragment()
    )


    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size


    override fun getPageTitle(position: Int): String? =

        context?.resources?.getStringArray(R.array.fragment_titulos_tab)?.get(position)

}