package com.ghedeon.trendroid.ui.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ghedeon.trendroid.R
import com.ghedeon.trendroid.common.inflate


class DetailsFragment : Fragment() {
	
	private var repoUrl: String? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			repoUrl = it.getString(URL_PARAM)
		}
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
		container?.inflate(R.layout.fragment_details)
	
	
	companion object {
		@JvmStatic
		fun newInstance(repoUrl: String) =
			DetailsFragment().apply {
				arguments = Bundle().apply {
					putString(URL_PARAM, repoUrl)
				}
			}
	}
}

private const val URL_PARAM = "repoUrl"