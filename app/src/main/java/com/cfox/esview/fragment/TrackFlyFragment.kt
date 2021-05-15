package com.cfox.esview.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.cfox.es_view.R
import com.cfox.esview.view.ShakeImageView
import com.cfox.esview.view.TrackFlyView

class TrackFlyFragment : Fragment(R.layout.fragment_track_fly) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val trackView1 = view.findViewById<TrackFlyView>(R.id.track_view_1)
        val trackView2 = view.findViewById<TrackFlyView>(R.id.track_view_2)
        val trackView3 = view.findViewById<TrackFlyView>(R.id.track_view_3)
        val trackView4 = view.findViewById<TrackFlyView>(R.id.track_view_4)

        val targetView = view.findViewById<ShakeImageView>(R.id.target_view)
        trackView1.targetView(targetView)
        trackView2.targetView(targetView)
        trackView3.targetView(targetView)
        trackView4.targetView(targetView)

        val listenerAdapter = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                targetView.startShake()
            }
        }

        trackView1.setListener(listenerAdapter)
        trackView2.setListener(listenerAdapter)
        trackView3.setListener(listenerAdapter)
        trackView4.setListener(listenerAdapter)

        view.findViewById<Button>(R.id.btn_start_button).setOnClickListener {
            val duration = 500L
            trackView1.startTrack(duration)
            trackView2.startTrack(duration + 100)
            trackView3.startTrack(duration + 200)
            trackView4.startTrack(duration + 300)
        }

        view.findViewById<Button>(R.id.btn_stop_button).setOnClickListener {
            trackView1.stopTrack()
            trackView2.stopTrack()
            trackView3.stopTrack()
            trackView4.stopTrack()
        }
    }

}