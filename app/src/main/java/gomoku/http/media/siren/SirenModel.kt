package gomoku.http.media.siren

class SirenModel<T, E>(
    val `class`: List<String>,
    val properties: T,
    val entities: List<Entity<E>>,
    val actions: List<Action>,
    val links: List<Link>,
    val recipeLinks: List<Link>,
)