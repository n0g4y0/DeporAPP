package io.github.n0g4y0.deporapp.ui.encuentro


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.adaptador.EncuentrosPagerAdapter
import kotlinx.android.synthetic.main.fragment_encuentros_view_pager.*

/**
 * mostrara el viewpager inicial, para mostrar los encuentros deportivos pendientes y concluidos
 */
class EncuentrosViewPagerFragment : Fragment() {

    private val adaptador by lazy { EncuentrosPagerAdapter(this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_encuentros_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pager.adapter = adaptador

        val tabLayoutMediator = TabLayoutMediator(tab_layout,
                                                    pager,
                                                    TabLayoutMediator.TabConfigurationStrategy { tab, position ->

                                                        when(position + 1){
                                                            1 -> {
                                                                tab.text = "Pendientes"
                                                            }
                                                            2 -> {
                                                                tab.text = "Concluidos"
                                                            }
                                                        }

                                                    })

                tabLayoutMediator.attach()
    }


}
