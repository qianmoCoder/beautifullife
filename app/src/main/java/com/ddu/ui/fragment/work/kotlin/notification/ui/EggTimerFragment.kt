package com.ddu.ui.fragment.work.kotlin.notification.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ddu.R
import com.ddu.databinding.FragmentEggTimerBinding

/**
 * Created by yzbzz on 2020/1/17.
 */
class EggTimerFragment : Fragment() {

    private val TOPIC = "breakfast"

    private val viewModel: EggTimerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentEggTimerBinding.inflate(inflater, container, false)
        binding.eggTimerViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        createChannel(
                getString(R.string.egg_notification_channel_id),
                getString(R.string.egg_notification_channel_name)
        )
        return binding.root
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(false)
            }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.breakfast_notification_channel_description)

            val notificationManager = requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        fun newInstance() = EggTimerFragment()
    }
}