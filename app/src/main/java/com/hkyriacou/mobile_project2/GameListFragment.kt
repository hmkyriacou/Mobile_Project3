package com.hkyriacou.mobile_project2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "GameListFragment"

class GameListFragment : Fragment() {



    private lateinit var gameRecyclerView: RecyclerView
    private var adapter:GameAdapter? = GameAdapter(emptyList())

    private val gameListViewModel: GameListViewModel by lazy {
        ViewModelProviders.of(this).get(GameListViewModel::class.java)
    }

    interface Callbacks {
        fun backToLanding()
        fun onGameSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_list, container, false)
        gameRecyclerView =
            view.findViewById(R.id.game_recycler_view) as RecyclerView
        gameRecyclerView.layoutManager = LinearLayoutManager(context)

        gameRecyclerView.adapter = adapter

        val returnButton : Button = view.findViewById(R.id.Return_GameList)
        returnButton.setOnClickListener{
            callbacks?.backToLanding()
        }

        return view }

    private inner class GameAdapter(var games: List<Game>)
        : RecyclerView.Adapter<GameHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : GameHolder {
            val view = layoutInflater.inflate(R.layout.list_item_game, parent, false)
            return GameHolder(view)
        }
        override fun getItemCount() = games.size
        override fun onBindViewHolder(holder: GameHolder, position: Int) {
            val game = games[position]
            holder.bind(game)
        } }

    private inner class GameHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val dateTextView: TextView = itemView.findViewById(R.id.game_date)
        private val teamsTextView: TextView = itemView.findViewById(R.id.game_Teams)
        private val scoresTextView: TextView = itemView.findViewById(R.id.game_score)
        private val homeImage: ImageView = itemView.findViewById(R.id.imageView3)
        private val guestImage: ImageView = itemView.findViewById(R.id.imageView4)

        private lateinit var game : Game
        fun bind(game: Game){
            this.game=game
            teamsTextView.text = "${game.teamAName} vs ${game.teamBName}"
            dateTextView.text = game.date.toString()
            scoresTextView.text = "${game.teamAScore}:${game.teamBScore}"
            homeImage.visibility = if (game.teamAScore > game.teamBScore) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            guestImage.visibility = if (game.teamBScore > game.teamAScore) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

        }
        override fun onClick(v: View?) {
            callbacks?.onGameSelected(game.id)
        }
        init{
            itemView.setOnClickListener(this)
        }
    }


    private fun updateUI(games: List<Game>) {
        adapter = GameAdapter(games)
        gameRecyclerView.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance(): GameListFragment {
            return GameListFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameListViewModel.gameListLiveData.observe(
            viewLifecycleOwner,
            { games ->
                games?.let {
                    Log.i(TAG, "Got games ${games.size}")
                    updateUI(games)
                }
            })

    }
}