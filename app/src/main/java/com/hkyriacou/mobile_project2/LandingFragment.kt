package com.hkyriacou.mobile_project2

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_landing.*
import java.util.*
import androidx.lifecycle.Observer
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val TAG = "LandingFragment"
private const val KEY_HOMESCORE = "homeScore"
private const val KEY_AWAYSCORE = "guestScore"
private const val REQUEST_PHOTO = 2

/**
 * A simple [Fragment] subclass.
 * Use the [LandingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LandingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: UUID? = null
    private var param2: String? = null
    private lateinit var game: Game
    private lateinit var photoFile: File
    private lateinit var photoUri: Uri


    private lateinit var homePhotoButton: ImageButton
    private lateinit var homePhotoView: ImageView
    private lateinit var awayPhotoButton: ImageButton
    private lateinit var awayPhotoView: ImageView
    private val gameDetailViewModel: GameDetailViewModel by lazy {
        ViewModelProviders.of(this).get(GameDetailViewModel::class.java)
    }

    interface Callbacks {
        fun moveToSavingFragment()
        fun moveToGameListFragment()
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        game = Game()
        arguments?.let {
            param1 = if(arguments?.getSerializable(ARG_PARAM1) == null){game.id}
                    else{arguments?.getSerializable(ARG_PARAM1) as UUID}
            param2 = it.getString(ARG_PARAM2)
        }
        param1?.let { gameDetailViewModel.loadGame(it) }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameDetailViewModel.gameLiveData.observe(
            viewLifecycleOwner,
            Observer { game ->
                game?.let {
                    this.game=game
                    photoFile = gameDetailViewModel.getPhotoFile(game)
                    photoUri = FileProvider.getUriForFile(requireActivity(),
                        "com.bignerdranch.android.criminalintent.fileprovider",
                        photoFile)

                    updateUI()
                }
            }
        )
    }
    private fun updateUI(){
        homeScore.text = game.teamAScore.toString()
        awayScore.text = game.teamBScore.toString()
        homeName.setText( game.teamAName )
        awayName.setText( game.teamBName )

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_landing, container, false)
        // ViewModel stuff
        homePhotoButton = view.findViewById(R.id.home_camera) as ImageButton
        homePhotoView = view.findViewById(R.id.home_photo) as ImageView
        awayPhotoButton = view.findViewById(R.id.away_camera) as ImageButton
        awayPhotoView = view.findViewById(R.id.away_photo) as ImageView

        /*awayPhotoButton.apply {
            val packageManager: PackageManager = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(
                    captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
            if (resolvedActivity == null) {
                isEnabled = false
            }
            setOnClickListener {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(
                        captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY
                    )
                for (cameraActivity in cameraActivities) {
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                startActivityForResult(captureImage, 1)
            }
        }*/


       /* awayPhotoButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_PHOTO)
        }*/



        // Button stuff

//        homeName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                game.teamAName = p0 as String
//            }
//
//            override fun afterTextChanged(s: Editable) {
//
//            }
//        })

        val btnH3 : Button = view.findViewById(R.id.btn3Home)
        btnH3.setOnClickListener {
            game.teamAScore+=3
            game.teamAName = homeName.text.toString()
            game.teamBName = awayName.text.toString()

            updateUI()
        }

        val btnA3 : Button = view.findViewById(R.id.btn3Away)
        btnA3.setOnClickListener {

            game.teamBScore+=3
            game.teamAName = homeName.text.toString()
            game.teamBName = awayName.text.toString()
            updateUI()

        }

        val btnH2 : Button = view.findViewById(R.id.btn2Home)
        btnH2.setOnClickListener {
            game.teamAScore+=2
            game.teamAName = homeName.text.toString()
            game.teamBName = awayName.text.toString()
            updateUI()

        }

        val btnA2 : Button = view.findViewById(R.id.btn2Away)
        btnA2.setOnClickListener {
            game.teamBScore+=2
            game.teamAName = homeName.text.toString()
            game.teamBName = awayName.text.toString()
            updateUI()

        }


        val btnH1 : Button = view.findViewById(R.id.btn1Home)
        btnH1.setOnClickListener {
            game.teamAScore+=1
            game.teamAName = homeName.text.toString()
            game.teamBName = awayName.text.toString()
            updateUI()

        }

        val btnA1 : Button = view.findViewById(R.id.btn1Away)
        btnA1.setOnClickListener {
            game.teamBScore+=1
            game.teamAName = homeName.text.toString()
            game.teamBName = awayName.text.toString()
            updateUI()

        }

        val reset : Button = view.findViewById(R.id.reset)
        reset.setOnClickListener {
            game.teamAScore=0

            game.teamBScore=0
            game.teamAName = homeName.text.toString()
            game.teamBName = awayName.text.toString()
            updateUI()

        }

        val saveButton : Button = view.findViewById(R.id.saveScore)
        saveButton.setOnClickListener {
            Log.d(TAG, "Moving to new Fragment")

            game.teamAName = homeName.text.toString()
            game.teamBName = awayName.text.toString()

            if(game.teamAScore + game.teamBScore !=0) {
                gameDetailViewModel.addGame(game)
            }

            callbacks?.moveToSavingFragment()
        }

        val listButton : Button = view.findViewById(R.id.gameList)
        listButton.setOnClickListener{
            Log.d(TAG, "Moving to GameList")
            callbacks?.moveToGameListFragment()
        }

        return view


    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onStart(){
        super.onStart()

       /* awayPhotoButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 1)
        }*/

        awayPhotoButton.apply {
                val packageManager: PackageManager = requireActivity().packageManager
                val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val resolvedActivity: ResolveInfo? =
                    packageManager.resolveActivity(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY)
                if (resolvedActivity == null) {
                    isEnabled = false
                }
                setOnClickListener {
                    captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    val cameraActivities: List<ResolveInfo> =
                        packageManager.queryIntentActivities(captureImage,
                            PackageManager.MATCH_DEFAULT_ONLY)
                    for (cameraActivity in cameraActivities) {
                        requireActivity().grantUriPermission(
                            cameraActivity.activityInfo.packageName,
                            photoUri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    }
                    startActivityForResult(captureImage, REQUEST_PHOTO)
                }
            }
        }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG,"hey whats up")
    }
    override fun onStop(){
        super.onStop()
        game.teamAName = homeName.text.toString()
        game.teamBName = awayName.text.toString()
        if(game.teamAScore + game.teamBScore !=0) {
            gameDetailViewModel.saveGame(game)
        }
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LandingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: UUID?, param2: String) =
            LandingFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}