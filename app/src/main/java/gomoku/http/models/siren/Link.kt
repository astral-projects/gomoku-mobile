package gomoku.http.models.siren

data class Link(
    val rel: Array<String>,
    val href: String
)

fun getHrefByRel(links: Array<Link>, relName: String): String? {
    links.forEach { link ->
        link.rel.forEach { rel ->
            if (rel == relName) {
                return link.href
            }
        }
    }
    return null
}
