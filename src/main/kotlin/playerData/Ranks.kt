package playerData

enum class Ranks(val prefix: String, val weight: Int) {

    PLAYER("<color:#BFBFBF>ᴘʟᴀʏᴇʀ ", 0),
    MODERATOR("<color:#77DD77>ᴍᴏᴅᴇʀᴀᴛᴏʀ ", 1),
    ADMIN("<color:#FF6961>ᴀᴅᴍɪɴ ", 2),
    OWNER("<color:#A7C7E7>ᴏᴡɴᴇʀ ", 3);

}