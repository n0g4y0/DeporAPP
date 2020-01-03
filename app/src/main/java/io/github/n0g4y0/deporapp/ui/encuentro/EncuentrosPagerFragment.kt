package io.github.n0g4y0.deporapp.ui.encuentro


import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.adaptador.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_encuentros_pager.*
import java.io.Serializable

/**
 * clase para manipular los encuentros concluidos y pendientes:
 */
class EncuentrosPagerFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_encuentros_pager,container, false)

        manejarTransaccion()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        iniciarAdaptador()
        iniciarTabLayout()
    }



    private fun manejarTransaccion() {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition()
    }

    private fun iniciarAdaptador() {
        fragmentManager?.let {fragmentManager ->



            ViewPagerAdapter(context, fragmentManager).also {
                view_pager.adapter = it
            }

        }
    }

    private fun iniciarTabLayout(){

        tab_layout.setupWithViewPager(view_pager)
    }





}
