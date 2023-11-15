package gomoku.services

import android.util.Log
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.leaderboard.domain.RankingInfo
import kotlinx.coroutines.delay
import pdm.gomoku.R
import kotlin.random.Random

private val TAG = FakeUsersServices::class.java.simpleName

class FakeUsersServices: UsersServices {
    private val users = listOf(
        PlayerInfo("John Doe", R.drawable.man5),
        PlayerInfo("Mary Jane", R.drawable.woman2),
        PlayerInfo("Peter Parker", R.drawable.man3),
        PlayerInfo("Jane Doe", R.drawable.woman4),
        PlayerInfo("John Smith", R.drawable.man2),
        PlayerInfo("Mary Smith", R.drawable.woman2),
        PlayerInfo("Peter Doe", R.drawable.man2),
        PlayerInfo("Jane Parker", R.drawable.woman3),
        PlayerInfo("John Smith", R.drawable.man4),
        PlayerInfo("Mary Smith", R.drawable.woman5),
        PlayerInfo("Peter Doe", R.drawable.man2),
    )

    private val usersStats = listOf(
        RankingInfo(PlayerInfo("John Doe", R.drawable.man5), 1, 999999, 999, 999, 999, 999),
        RankingInfo(
            PlayerInfo("Mary Jane", R.drawable.woman2),
            2,
            999998,
            999998,
            999998,
            999998,
            999
        ),
        RankingInfo(
            PlayerInfo("Peter Parker", R.drawable.man3),
            3,
            999997,
            999997,
            999997,
            999997,
            999
        ),
        RankingInfo(PlayerInfo("Jane Doe", R.drawable.woman4), 4, 999996, 999996, 999996, 999, 999),
        RankingInfo(PlayerInfo("John Smith", R.drawable.man2), 5, 999995, 999995, 999995, 999, 999),
        RankingInfo(
            PlayerInfo("Mary Smith", R.drawable.woman2),
            6,
            999994,
            999994,
            999994,
            999,
            999
        ),
        RankingInfo(PlayerInfo("Peter Doe", R.drawable.man2), 7, 999993, 999993, 999993, 999, 999),
        RankingInfo(
            PlayerInfo("Jane Parker", R.drawable.woman3),
            8,
            999992,
            999992,
            999992,
            999,
            999
        ),
        RankingInfo(PlayerInfo("John Smith", R.drawable.man4), 9, 999991, 999991, 999991, 999, 999),
        RankingInfo(
            PlayerInfo("Mary Smith", R.drawable.woman5),
            10,
            999990,
            999990,
            999990,
            999,
            999
        ),
        RankingInfo(PlayerInfo("Peter Doe", R.drawable.man2), 11, 999989, 999989, 999989, 999, 999),
        RankingInfo(PlayerInfo("John Doe", R.drawable.man5), 12, 999988, 999988, 999988, 999, 999),
        RankingInfo(
            PlayerInfo("Mary Jane", R.drawable.woman2),
            13,
            999987,
            999987,
            999987,
            999,
            999
        ),
        RankingInfo(
            PlayerInfo("Peter Parker", R.drawable.man3),
            14,
            999986,
            999986,
            999986,
            999,
            999
        ),
        RankingInfo(
            PlayerInfo("Jane Doe", R.drawable.woman4),
            15,
            999985,
            999985,
            999985,
            999,
            999
        ),
        RankingInfo(
            PlayerInfo("John Smith", R.drawable.man2),
            16,
            999984,
            999984,
            999984,
            999,
            999
        ),


        )

    override suspend fun fetchUsers(): List<PlayerInfo> {
        Log.v(TAG, "fetching users...")
        delay(3000) // simulate network delay
        Log.v(TAG, "users fetched")
        return users
    }

    override suspend fun fetchUser(id: Int): PlayerInfo {
        Log.v(TAG, "fetching user with id $id...")
        delay(3000) // simulate network delay
        val index = Random.nextInt(from = 0, until = users.size)
        Log.v(TAG, "user fetched")
        return users[index]
    }

    override suspend fun fetchUsersStats(): List<RankingInfo> {
        Log.v(TAG, "fetching users stats...")
        delay(3000) // simulate network delay
        val index = Random.nextInt(from = 0, until = usersStats.size)
        Log.v(TAG, "users stats fetched")
        return usersStats
    }
}