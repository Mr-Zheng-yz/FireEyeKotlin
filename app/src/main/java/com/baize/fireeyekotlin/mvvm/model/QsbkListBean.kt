package com.baize.fireeyekotlin.mvvm.model

data class QsbkListBean(var count: Int = 0,
                        var err: Int = 0,
                        var total: Int = 0,
                        var page: Int = 0,
                        var refresh: Int = 0,
                        var items: List<ItemsBean>? = null) {

    data class ItemsBean(var format: String? = null,
                         var image: String? = null,
                         var published_at: Int = 0,
                         var tag: String? = null,
                         var user: UserBean? = null,
                         var image_size: Any? = null,
                         var is_prefer: Any? = null,
                         var id: Int = 0,
                         var votes: VotesBean? = null,
                         var created_at: Int = 0,
                         var content: String? = null,
                         var state: String? = null,
                         var is_promote: Any? = null,
                         var comments_count: Int = 0,
                         var isAllow_comment: Boolean = false,
                         var share_count: Int = 0,
                         var topic: TopicBean? = null) {

        data class UserBean(var thumb_webp: String? = null,
                            var uid: Int = 0,
                            var updated_at: Int = 0,
                            var medium: String? = null,
                            var id: Int = 0,
                            var icon: String? = null,
                            var avatar_updated_at: Int = 0,
                            var thumb: String? = null,
                            var last_visited_at: Int = 0,
                            var gender: String? = null,
                            var age: Int = 0,
                            var medium_webp: String? = null,
                            var state: String? = null,
                            var role: String? = null,
                            var rel: String? = null,
                            var astrology: String? = null,
                            var login: String? = null,
                            var last_device: String? = null,
                            var created_at: Int = 0)

        data class VotesBean(var down: Int = 0,
                             var up: Int = 0)

        data class TopicBean(var introduction: String? = null,
                             var content: String? = null,
                             var avatar: String? = null,
                             var background: String? = null,
                             var type: Int = 0,
                             var id: Int = 0)
    }

    override fun toString(): String {
        return "QsbkListBean{" +
                "count=" + count +
                ", err=" + err +
                ", total=" + total +
                ", page=" + page +
                ", refresh=" + refresh +
                ", items=" + items +
                '}'
    }
}
