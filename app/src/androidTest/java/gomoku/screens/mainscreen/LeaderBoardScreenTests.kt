package gomoku.screens.mainscreen

class LeaderBoardScreenTests {
    /*
    @get:Rule
    val composeTestRule = createComposeRule()

    //TODO(not done because i need to talk with teacher first to know how to do this test)

    @Test
    fun try_to_click_on_burger_menu_and_check_if_it_opens() {
        //Arrange
        composeTestRule.setContent {
            val nPlayers = 200
            val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
            LeaderboardScreen(
                rankingInfo = playersRankingInfo.first(),
                getItemsFromPage = { page ->
                    Leaderboard.paginatedRankingInfo(
                        list = playersRankingInfo,
                        page = page
                    )
                },
                onSearchRequest = { term ->
                    playersRankingInfo.filter {
                        it.playerInfo.name.contains(
                            term.value,
                            ignoreCase = true
                        )
                    }
                },
                toFindGameScreen = {},
                toAboutScreen = {},
                onLogoutRequest = {}
            )
        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuButton).performClick()
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_find_game() {
        var findGameWasCalled = false

        composeTestRule.setContent {
            val nPlayers = 200
            val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
            LeaderboardScreen(
                rankingInfo = playersRankingInfo.first(),
                getItemsFromPage = { page ->
                    Leaderboard.paginatedRankingInfo(
                        list = playersRankingInfo,
                        page = page
                    )
                },
                onSearchRequest = { term ->
                    playersRankingInfo.filter {
                        it.playerInfo.name.contains(
                            term.value,
                            ignoreCase = true
                        )
                    }
                },
                toFindGameScreen = { findGameWasCalled = true },
                toAboutScreen = {},
                onLogoutRequest = {}
            )
        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuFindGameButton).performClick()

        //Assert
        assertTrue(findGameWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_about() {
        var aboutWasCalled = false

        composeTestRule.setContent {
            val nPlayers = 200
            val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
            LeaderboardScreen(
                rankingInfo = playersRankingInfo.first(),
                getItemsFromPage = { page ->
                    Leaderboard.paginatedRankingInfo(
                        list = playersRankingInfo,
                        page = page
                    )
                },
                onSearchRequest = { term ->
                    playersRankingInfo.filter {
                        it.playerInfo.name.contains(
                            term.value,
                            ignoreCase = true
                        )
                    }
                },
                toFindGameScreen = {},
                toAboutScreen = { aboutWasCalled = true },
                onLogoutRequest = {}
            )
        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuAboutButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuAboutButton).performClick()

        //Assert
        assertTrue(aboutWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_logout() {
        var logoutWasCalled = false

        composeTestRule.setContent {
            val nPlayers = 200
            val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
            LeaderboardScreen(
                rankingInfo = playersRankingInfo.first(),
                getItemsFromPage = { page ->
                    Leaderboard.paginatedRankingInfo(
                        list = playersRankingInfo,
                        page = page
                    )
                },
                onSearchRequest = { term ->
                    playersRankingInfo.filter {
                        it.playerInfo.name.contains(
                            term.value,
                            ignoreCase = true
                        )
                    }
                },
                toFindGameScreen = {},
                toAboutScreen = {},
                onLogoutRequest = { logoutWasCalled = true }
            )
        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuLogoutButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuLogoutButton).performClick()

        //Assert
        assertTrue(logoutWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_switch_theme() {
        //Arrange
        composeTestRule.setContent {
            val nPlayers = 200
            val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
            LeaderboardScreen(
                rankingInfo = playersRankingInfo.first(),
                getItemsFromPage = { page ->
                    Leaderboard.paginatedRankingInfo(
                        list = playersRankingInfo,
                        page = page
                    )
                },
                onSearchRequest = { term ->
                    playersRankingInfo.filter {
                        it.playerInfo.name.contains(
                            term.value,
                            ignoreCase = true
                        )
                    }
                },
                toFindGameScreen = {},
                toAboutScreen = {},
                onLogoutRequest = {}
            )
        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).performClick()

        //Assert
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).assertExists()

    }*/
}