package gomoku.domain.service.media.siren

class SirenModel<T>(
    val classList: List<String>,
    val properties: T,
    val entities: List<Entity<Any>>,
    val actions: List<Action>,
    val links: List<Link>,
    val recipeLinks: List<Link>
)