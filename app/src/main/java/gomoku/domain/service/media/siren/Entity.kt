package gomoku.domain.service.media.siren

class Entity<T>(
    val properties: T,
    val links: List<Link>,
    val rel: List<String>
)