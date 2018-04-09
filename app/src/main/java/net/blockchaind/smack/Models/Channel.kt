package net.blockchaind.smack.Models

/**
 * Created by shiva on 2/17/2018.
 */
class Channel(val name: String, val description: String, val id: String) {
    override fun toString(): String {
        return "#$name"
    }
}