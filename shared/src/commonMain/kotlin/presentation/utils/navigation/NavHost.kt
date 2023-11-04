package presentation.utils.navigation
//
//import androidx.compose.runtime.Composable
//
//class NavHost(
//    val navController: NavController,
//    val contents: @Composable NavigationGraphBuilder.() -> Unit,
//) {
//    @Composable
//    fun build() {
//        NavigationGraphBuilder(navController).renderContents()
//    }
//    inner class NavigationGraphBuilder(
//        val navController: NavController = this@NavHost.navController
//    ) {
//        @Composable
//        fun renderContents() {
//            this@NavHost.contents(this)
//        }
//    }
//}
//
///**
// * Composable to build the Navigation Host
// * */
//@Composable
//fun NavHost.NavigationGraphBuilder.composable(
//    route: String,
//    content: @Composable () -> Unit
//) {
//    if (navController.currentScreen.value == route) {
//        content()
//    }
//}
