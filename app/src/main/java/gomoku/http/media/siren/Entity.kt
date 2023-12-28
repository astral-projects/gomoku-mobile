package gomoku.http.media.siren

class Entity<T>(
    val properties: T,
    val links: List<Link>,
    val rel: List<String>
)