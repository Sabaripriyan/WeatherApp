package app.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.jar.Attributes

@Entity
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val location: String,
    val email: String,
    val phone: String,
    val picture: String
)