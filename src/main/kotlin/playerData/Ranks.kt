package playerData

enum class Ranks(val prefix: String, val weight: Int) {

    PLAYER("<color:#BFBFBF>ᴘʟᴀʏᴇʀ ", 0),
    MODERATOR("<color:#77DD77>ᴍᴏᴅᴇʀᴀᴛᴏʀ ", 1),
    ADMIN("<b><gradient:#FF6961:#FFA6CA>ᴀᴅᴍɪɴ</gradient></b> ", 2),
    OWNER("<b><gradient:#8968CD:#FFA6CA>ᴏᴡɴᴇʀ</gradient></b> ", 3);
}