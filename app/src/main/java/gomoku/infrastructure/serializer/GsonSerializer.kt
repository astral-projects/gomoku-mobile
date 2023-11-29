package gomoku.infrastructure.serializer

interface GsonSerializer<T> {
    fun serialize(data: T): String
    fun deserialize(data: String): T
}