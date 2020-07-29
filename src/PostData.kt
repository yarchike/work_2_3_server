package com.martynov

import com.google.gson.Gson
import com.martynov.model.PostModel
import com.martynov.model.PostTypes
import sun.rmi.runtime.Log
import java.io.File

object PostData {

    fun getDataBase(): ArrayList<PostModel> {
        val list = ArrayList<PostModel>()
        val fileName = "posts.json"
        //val list2 = Gson().fromJson<List<PostModel>>(File(fileName).readText())
                list.add(
            PostModel(
                2,
                1594585699570,
                "Мартынов Я.В.",
                "10 февраля, среда\n" +
                        "\n" +
                        "Львам так надоело терять людей! \n" +
                        "Им нужен тот, кто придет в их жизнь \n" +
                        "и скажет: 'Хочешь, не хочешь, а я остаюсь",
                15,
                0,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.Events,
                hidePost = true
            )
        )
        list.add(
            PostModel(
                3,
                1594585699570,
                "Мартынов Я.В.",
                "https://www.youtube.com/watch?v=jaVljl8mdqI",
                0,
                12,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.YoutubeVideo
            )
        )

        list.add(
            PostModel(
                4,
                1594585699570,
                "Мартынов Я.В.",
                "10 февраля, среда\n" +
                        "\n" +
                        "Львам так надоело терять людей! \n" +
                        "Им нужен тот, кто придет в их жизнь \n" +
                        "и скажет: 'Хочешь, не хочешь, а я остаюсь",
                15,
                0,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.Events
            )
        )
        list.add(
            PostModel(
                5,
                1594585699570,
                "Мартынов Я.В.",
                "10 февраля, среда\n" +
                        "\n" +
                        "Львам так надоело терять людей! \n" +
                        "Им нужен тот, кто придет в их жизнь \n" +
                        "и скажет: 'Хочешь, не хочешь, а я остаюсь",
                15,
                0,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.Reposts,
                autorRepost = "Захаровская Евгения",
                dateRepost = 1595184329249
            )
        )
        list.add(
            PostModel(
                6,
                1594585699570,
                "Мартынов Я.В.",
                "10 февраля, среда\n" +
                        "\n" +
                        "Львам так надоело терять людей! \n" +
                        "Им нужен тот, кто придет в их жизнь \n" +
                        "и скажет: 'Хочешь, не хочешь, а я остаюсь",
                15,
                0,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.Events,
                hidePost = true
            )
        )
        list.add(
            PostModel(
                7,
                1594585699570,
                "Мартынов Я.В.",
                "https://www.youtube.com/watch?v=jaVljl8mdqI",
                0,
                12,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.YoutubeVideo
            )
        )

        list.add(
            PostModel(
                8,
                1594585699570,
                "Мартынов Я.В.",
                "10 февраля, среда\n" +
                        "\n" +
                        "Львам так надоело терять людей! \n" +
                        "Им нужен тот, кто придет в их жизнь \n" +
                        "и скажет: 'Хочешь, не хочешь, а я остаюсь",
                15,
                0,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.Events
            )
        )
        list.add(
            PostModel(
                9,
                1594585699570,
                "Мартынов Я.В.",
                "10 февраля, среда\n" +
                        "\n" +
                        "Львам так надоело терять людей! \n" +
                        "Им нужен тот, кто придет в их жизнь \n" +
                        "и скажет: 'Хочешь, не хочешь, а я остаюсь",
                15,
                0,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.Reposts,
                autorRepost = "Захаровская Евгения",
                dateRepost = 1595184329249
            )
        )
        list.add(
            PostModel(
                10,
                1594585699570,
                "Мартынов Я.В.",
                "10 февраля, среда\n" +
                        "\n" +
                        "Львам так надоело терять людей! \n" +
                        "Им нужен тот, кто придет в их жизнь \n" +
                        "и скажет: 'Хочешь, не хочешь, а я остаюсь",
                15,
                0,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.Events,
                hidePost = true
            )
        )
        list.add(
            PostModel(
                11,
                1594585699570,
                "Мартынов Я.В.",
                "https://www.youtube.com/watch?v=jaVljl8mdqI",
                0,
                12,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.YoutubeVideo
            )
        )

        list.add(
            PostModel(
                12,
                1594585699570,
                "Мартынов Я.В.",
                "10 февраля, среда\n" +
                        "\n" +
                        "Львам так надоело терять людей! \n" +
                        "Им нужен тот, кто придет в их жизнь \n" +
                        "и скажет: 'Хочешь, не хочешь, а я остаюсь",
                15,
                0,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.Events
            )
        )
        list.add(
            PostModel(
                13,
                1594585699570,
                "Мартынов Я.В.",
                "10 февраля, среда\n" +
                        "\n" +
                        "Львам так надоело терять людей! \n" +
                        "Им нужен тот, кто придет в их жизнь \n" +
                        "и скажет: 'Хочешь, не хочешь, а я остаюсь",
                15,
                0,
                5,
                isLike = false,
                isComment = false,
                isShare = false,
                adress = "Проспект Королева дом 5",
                coordinates = Pair(55.921606, 37.841268),
                type = PostTypes.Reposts,
                autorRepost = "Захаровская Евгения",
                dateRepost = 1595184329249
            )
        )
        return list
    }
}