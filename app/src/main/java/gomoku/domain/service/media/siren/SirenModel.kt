package gomoku.domain.service.media.siren

class SirenModel<T, E>(
    val classList: List<String>,
    val properties: T,
    val entities: List<Entity<E>>,
    val actions: List<Action>,
    val links: List<Link>,
    val recipeLinks: List<Link>
)