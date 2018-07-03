package com.cicinnus.cateye.module.discover;

import android.app.Activity;

import com.cicinnus.cateye.tools.GsonUtil;
import com.cicinnus.retrofitlib.base.BaseMVPPresenter;
import com.cicinnus.retrofitlib.net.error.ExceptionHandle;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/1/18.
 */

public class DiscoverPresenter extends BaseMVPPresenter<DiscoverContract.IDiscoverView> implements DiscoverContract.IDiscoverPresenter {

    private final DiscoverManager discoverManager;
    
    private final String mock_json = "{\n" +
            "        \"feeds\": [{\n" +
            "            \"commentCount\": 7,\n" +
            "            \"description\": \"\",\n" +
            "            \"feedType\": 7,\n" +
            "            \"id\": 54981,\n" +
            "            \"images\": [{\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 416,\n" +
            "                \"id\": 3162977,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34223,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p0.meituan" +
            ".net/movie/c38bcdcc4fac908223d353110f1754da112056.jpg@300w_225h_1e_1c\",\n" +
            "                \"width\": 416\n" +
            "            }],\n" +
            "            \"latestFeedTime\": 0,\n" +
            "            \"rank\": 0,\n" +
            "            \"style\": 2,\n" +
            "            \"time\": 1518183120000,\n" +
            "            \"title\": \"《变形金刚》导演加入DC宇宙？漫改电影《暴狼》疑似提上日程\",\n" +
            "            \"upCount\": 9,\n" +
            "            \"updated\": 1518183090000,\n" +
            "            \"url\": \"meituanmovie://www.meituan.com/forum/newsDetail?id=34223\",\n" +
            "            \"user\": {\n" +
            "                \"age\": \"25\",\n" +
            "                \"authInfo\": \"\",\n" +
            "                \"avatarType\": 255,\n" +
            "                \"avatarurl\": \"https://img.meituan" +
            ".net/avatar/1fae1268d70810edf668395dc7a74bfc18495.jpg\",\n" +
            "                \"birthday\": -2240553957000,\n" +
            "                \"city\": {\n" +
            "                    \"deleted\": false,\n" +
            "                    \"id\": 1,\n" +
            "                    \"nm\": \"北京\",\n" +
            "                    \"py\": \"beijing\"\n" +
            "                },\n" +
            "                \"currentExp\": 0,\n" +
            "                \"gender\": 0,\n" +
            "                \"id\": 332456130,\n" +
            "                \"interest\": \"\",\n" +
            "                \"juryLevel\": 0,\n" +
            "                \"maoyanAge\": \"1.5\",\n" +
            "                \"marriage\": \"\",\n" +
            "                \"nextTitle\": \"\",\n" +
            "                \"nickName\": \"猫眼资讯\",\n" +
            "                \"occupation\": \"\",\n" +
            "                \"registerTime\": 1459394732000,\n" +
            "                \"roleType\": 0,\n" +
            "                \"signature\": \"\",\n" +
            "                \"title\": \"青铜会员\",\n" +
            "                \"topicCount\": 1,\n" +
            "                \"totalExp\": 130,\n" +
            "                \"uid\": 0,\n" +
            "                \"userLevel\": 1,\n" +
            "                \"userNextLevel\": 2,\n" +
            "                \"username\": \"maoyan_movie\",\n" +
            "                \"vipInfo\": \"官方\",\n" +
            "                \"vipType\": 1,\n" +
            "                \"visitorCount\": 41\n" +
            "            },\n" +
            "            \"viewCount\": 4473\n" +
            "        }, {\n" +
            "            \"commentCount\": 1,\n" +
            "            \"description\": \"\",\n" +
            "            \"feedType\": 7,\n" +
            "            \"id\": 54980,\n" +
            "            \"images\": [{\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 1000,\n" +
            "                \"id\": 3162965,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34222,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p0.meituan" +
            ".net/movie/71bf2b7e94fd49b5b2b33bd29dd4d25e747520.jpg@300w_225h_1e_1c\",\n" +
            "                \"width\": 1000\n" +
            "            }],\n" +
            "            \"latestFeedTime\": 0,\n" +
            "            \"rank\": 0,\n" +
            "            \"style\": 2,\n" +
            "            \"time\": 1518180960000,\n" +
            "            \"title\": \"猫眼演出扬帆起航，为演出市场带来新变量\",\n" +
            "            \"upCount\": 1,\n" +
            "            \"updated\": 1518180985000,\n" +
            "            \"url\": \"meituanmovie://www.meituan.com/forum/newsDetail?id=34222\",\n" +
            "            \"user\": {\n" +
            "                \"age\": \"25\",\n" +
            "                \"authInfo\": \"\",\n" +
            "                \"avatarType\": 255,\n" +
            "                \"avatarurl\": \"https://img.meituan" +
            ".net/avatar/1fae1268d70810edf668395dc7a74bfc18495.jpg\",\n" +
            "                \"birthday\": -2240553957000,\n" +
            "                \"city\": {\n" +
            "                    \"deleted\": false,\n" +
            "                    \"id\": 1,\n" +
            "                    \"nm\": \"北京\",\n" +
            "                    \"py\": \"beijing\"\n" +
            "                },\n" +
            "                \"currentExp\": 0,\n" +
            "                \"gender\": 0,\n" +
            "                \"id\": 332456130,\n" +
            "                \"interest\": \"\",\n" +
            "                \"juryLevel\": 0,\n" +
            "                \"maoyanAge\": \"1.5\",\n" +
            "                \"marriage\": \"\",\n" +
            "                \"nextTitle\": \"\",\n" +
            "                \"nickName\": \"猫眼资讯\",\n" +
            "                \"occupation\": \"\",\n" +
            "                \"registerTime\": 1459394732000,\n" +
            "                \"roleType\": 0,\n" +
            "                \"signature\": \"\",\n" +
            "                \"title\": \"青铜会员\",\n" +
            "                \"topicCount\": 1,\n" +
            "                \"totalExp\": 130,\n" +
            "                \"uid\": 0,\n" +
            "                \"userLevel\": 1,\n" +
            "                \"userNextLevel\": 2,\n" +
            "                \"username\": \"maoyan_movie\",\n" +
            "                \"vipInfo\": \"官方\",\n" +
            "                \"vipType\": 1,\n" +
            "                \"visitorCount\": 41\n" +
            "            },\n" +
            "            \"viewCount\": 5618\n" +
            "        }, {\n" +
            "            \"commentCount\": 13,\n" +
            "            \"description\": \"\",\n" +
            "            \"feedType\": 7,\n" +
            "            \"id\": 54979,\n" +
            "            \"images\": [{\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 1000,\n" +
            "                \"id\": 3162962,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34221,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p1.meituan" +
            ".net/movie/7fad508e8c7f1147fd4b36e5e60d66b3831488.jpg@300w_225h_1e_1c\",\n" +
            "                \"width\": 1000\n" +
            "            }],\n" +
            "            \"latestFeedTime\": 0,\n" +
            "            \"rank\": 0,\n" +
            "            \"style\": 2,\n" +
            "            \"time\": 1518179940000,\n" +
            "            \"title\": \"奥斯卡13项提名，威尼斯夺金狮，这部《水形物语》内地终过审！\",\n" +
            "            \"upCount\": 15,\n" +
            "            \"updated\": 1518179952000,\n" +
            "            \"url\": \"meituanmovie://www.meituan.com/forum/newsDetail?id=34221\",\n" +
            "            \"user\": {\n" +
            "                \"age\": \"25\",\n" +
            "                \"authInfo\": \"\",\n" +
            "                \"avatarType\": 255,\n" +
            "                \"avatarurl\": \"https://img.meituan" +
            ".net/avatar/1fae1268d70810edf668395dc7a74bfc18495.jpg\",\n" +
            "                \"birthday\": -2240553957000,\n" +
            "                \"city\": {\n" +
            "                    \"deleted\": false,\n" +
            "                    \"id\": 1,\n" +
            "                    \"nm\": \"北京\",\n" +
            "                    \"py\": \"beijing\"\n" +
            "                },\n" +
            "                \"currentExp\": 0,\n" +
            "                \"gender\": 0,\n" +
            "                \"id\": 332456130,\n" +
            "                \"interest\": \"\",\n" +
            "                \"juryLevel\": 0,\n" +
            "                \"maoyanAge\": \"1.5\",\n" +
            "                \"marriage\": \"\",\n" +
            "                \"nextTitle\": \"\",\n" +
            "                \"nickName\": \"猫眼资讯\",\n" +
            "                \"occupation\": \"\",\n" +
            "                \"registerTime\": 1459394732000,\n" +
            "                \"roleType\": 0,\n" +
            "                \"signature\": \"\",\n" +
            "                \"title\": \"青铜会员\",\n" +
            "                \"topicCount\": 1,\n" +
            "                \"totalExp\": 130,\n" +
            "                \"uid\": 0,\n" +
            "                \"userLevel\": 1,\n" +
            "                \"userNextLevel\": 2,\n" +
            "                \"username\": \"maoyan_movie\",\n" +
            "                \"vipInfo\": \"官方\",\n" +
            "                \"vipType\": 1,\n" +
            "                \"visitorCount\": 41\n" +
            "            },\n" +
            "            \"viewCount\": 5782\n" +
            "        }, {\n" +
            "            \"commentCount\": 11,\n" +
            "            \"description\": \"\",\n" +
            "            \"feedType\": 7,\n" +
            "            \"id\": 54977,\n" +
            "            \"images\": [{\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 800,\n" +
            "                \"id\": 3162929,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34219,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p0.meituan" +
            ".net/movie/e26f0ea1a8c9b29f1aee24494697a74e270659.jpg@300w_225h_1e_1c\",\n" +
            "                \"width\": 800\n" +
            "            }, {\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 800,\n" +
            "                \"id\": 3162930,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34219,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p1.meituan" +
            ".net/movie/2ed050143a4ea9e027915c92127bf0a7202739.jpg@300w_225h_1e_1c\",\n" +
            "                \"width\": 800\n" +
            "            }, {\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 260,\n" +
            "                \"id\": 3162931,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34219,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p1.meituan" +
            ".net/movie/a91bb9a497f89dc3f31667457039edfb40068.jpg@300w_225h_1e_1c\",\n" +
            "                \"width\": 260\n" +
            "            }],\n" +
            "            \"latestFeedTime\": 0,\n" +
            "            \"rank\": 0,\n" +
            "            \"style\": 3,\n" +
            "            \"time\": 1518175500000,\n" +
            "            \"title\": \"DC新作《夜翼》进度曝光，剧本很快出炉，近期不选角\",\n" +
            "            \"upCount\": 13,\n" +
            "            \"updated\": 1518175511000,\n" +
            "            \"url\": \"meituanmovie://www.meituan.com/forum/newsDetail?id=34219\",\n" +
            "            \"user\": {\n" +
            "                \"age\": \"25\",\n" +
            "                \"authInfo\": \"\",\n" +
            "                \"avatarType\": 255,\n" +
            "                \"avatarurl\": \"https://img.meituan" +
            ".net/avatar/1fae1268d70810edf668395dc7a74bfc18495.jpg\",\n" +
            "                \"birthday\": -2240553957000,\n" +
            "                \"city\": {\n" +
            "                    \"deleted\": false,\n" +
            "                    \"id\": 1,\n" +
            "                    \"nm\": \"北京\",\n" +
            "                    \"py\": \"beijing\"\n" +
            "                },\n" +
            "                \"currentExp\": 0,\n" +
            "                \"gender\": 0,\n" +
            "                \"id\": 332456130,\n" +
            "                \"interest\": \"\",\n" +
            "                \"juryLevel\": 0,\n" +
            "                \"maoyanAge\": \"1.5\",\n" +
            "                \"marriage\": \"\",\n" +
            "                \"nextTitle\": \"\",\n" +
            "                \"nickName\": \"猫眼资讯\",\n" +
            "                \"occupation\": \"\",\n" +
            "                \"registerTime\": 1459394732000,\n" +
            "                \"roleType\": 0,\n" +
            "                \"signature\": \"\",\n" +
            "                \"title\": \"青铜会员\",\n" +
            "                \"topicCount\": 1,\n" +
            "                \"totalExp\": 130,\n" +
            "                \"uid\": 0,\n" +
            "                \"userLevel\": 1,\n" +
            "                \"userNextLevel\": 2,\n" +
            "                \"username\": \"maoyan_movie\",\n" +
            "                \"vipInfo\": \"官方\",\n" +
            "                \"vipType\": 1,\n" +
            "                \"visitorCount\": 41\n" +
            "            },\n" +
            "            \"viewCount\": 8974\n" +
            "        }, {\n" +
            "            \"commentCount\": 2,\n" +
            "            \"description\": \"\",\n" +
            "            \"feedType\": 7,\n" +
            "            \"id\": 54976,\n" +
            "            \"images\": [{\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 720,\n" +
            "                \"id\": 3162925,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34218,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p0.meituan" +
            ".net/movie/11f737f35c007675f0a968a4d248434d699211.png@300w_225h_1e_1c\",\n" +
            "                \"width\": 720\n" +
            "            }, {\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 645,\n" +
            "                \"id\": 3162926,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34218,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p1.meituan" +
            ".net/movie/0767db4f6d0aef04a44603793c23f991787466.png@300w_225h_1e_1c\",\n" +
            "                \"width\": 645\n" +
            "            }, {\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 387,\n" +
            "                \"id\": 3162927,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34218,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p1.meituan" +
            ".net/movie/8f18186941f6865d6637cfbbc960a1a4359653.png@300w_225h_1e_1c\",\n" +
            "                \"width\": 387\n" +
            "            }],\n" +
            "            \"latestFeedTime\": 0,\n" +
            "            \"rank\": 0,\n" +
            "            \"style\": 3,\n" +
            "            \"time\": 1518173460000,\n" +
            "            \"title\": \"《一路繁花相送》开播，钟汉良能守住他的偶像剧天王地位吗？\",\n" +
            "            \"upCount\": 7,\n" +
            "            \"updated\": 1518173466000,\n" +
            "            \"url\": \"meituanmovie://www.meituan.com/forum/newsDetail?id=34218\",\n" +
            "            \"user\": {\n" +
            "                \"age\": \"25\",\n" +
            "                \"authInfo\": \"\",\n" +
            "                \"avatarType\": 0,\n" +
            "                \"avatarurl\": \"\",\n" +
            "                \"birthday\": -2240553957000,\n" +
            "                \"currentExp\": 0,\n" +
            "                \"gender\": 0,\n" +
            "                \"id\": 976532154,\n" +
            "                \"interest\": \"\",\n" +
            "                \"juryLevel\": 0,\n" +
            "                \"maoyanAge\": \"0\",\n" +
            "                \"marriage\": \"\",\n" +
            "                \"nextTitle\": \"\",\n" +
            "                \"nickName\": \"猫眼电视\",\n" +
            "                \"occupation\": \"\",\n" +
            "                \"registerTime\": 1503568355000,\n" +
            "                \"roleType\": 0,\n" +
            "                \"signature\": \"\",\n" +
            "                \"title\": \"青铜会员\",\n" +
            "                \"topicCount\": 1,\n" +
            "                \"totalExp\": 130,\n" +
            "                \"uid\": 0,\n" +
            "                \"userLevel\": 1,\n" +
            "                \"userNextLevel\": 2,\n" +
            "                \"username\": \"猫眼电视\",\n" +
            "                \"vipInfo\": \"\",\n" +
            "                \"vipType\": 0,\n" +
            "                \"visitorCount\": 2\n" +
            "            },\n" +
            "            \"viewCount\": 8745\n" +
            "        }, {\n" +
            "            \"commentCount\": 6,\n" +
            "            \"description\": \"\",\n" +
            "            \"feedType\": 7,\n" +
            "            \"id\": 54972,\n" +
            "            \"images\": [{\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 1000,\n" +
            "                \"id\": 3162905,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34215,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p0.meituan" +
            ".net/movie/176664f73506f1037ba63374240e7f401069056.jpg@300w_225h_1e_1c\",\n" +
            "                \"width\": 1000\n" +
            "            }, {\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 148,\n" +
            "                \"id\": 3162906,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34215,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p1.meituan" +
            ".net/movie/ce31be0b84b11898dfe25bcc152c727228241.jpg@300w_225h_1e_1c\",\n" +
            "                \"width\": 148\n" +
            "            }, {\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 344,\n" +
            "                \"id\": 3162907,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34215,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p1.meituan" +
            ".net/movie/4e82945e54b451c5f2da94dd39c46df897200.jpg@300w_225h_1e_1c\",\n" +
            "                \"width\": 344\n" +
            "            }],\n" +
            "            \"latestFeedTime\": 0,\n" +
            "            \"rank\": 0,\n" +
            "            \"style\": 3,\n" +
            "            \"time\": 1518169800000,\n" +
            "            \"title\": \"迪士尼影业年度巨制亮相超级碗 精彩预告引爆全新期待\",\n" +
            "            \"upCount\": 31,\n" +
            "            \"updated\": 1518169799000,\n" +
            "            \"url\": \"meituanmovie://www.meituan.com/forum/newsDetail?id=34215\",\n" +
            "            \"user\": {\n" +
            "                \"age\": \"25\",\n" +
            "                \"authInfo\": \"\",\n" +
            "                \"avatarType\": 255,\n" +
            "                \"avatarurl\": \"https://img.meituan" +
            ".net/avatar/1fae1268d70810edf668395dc7a74bfc18495.jpg\",\n" +
            "                \"birthday\": -2240553957000,\n" +
            "                \"city\": {\n" +
            "                    \"deleted\": false,\n" +
            "                    \"id\": 1,\n" +
            "                    \"nm\": \"北京\",\n" +
            "                    \"py\": \"beijing\"\n" +
            "                },\n" +
            "                \"currentExp\": 0,\n" +
            "                \"gender\": 0,\n" +
            "                \"id\": 332456130,\n" +
            "                \"interest\": \"\",\n" +
            "                \"juryLevel\": 0,\n" +
            "                \"maoyanAge\": \"1.5\",\n" +
            "                \"marriage\": \"\",\n" +
            "                \"nextTitle\": \"\",\n" +
            "                \"nickName\": \"猫眼资讯\",\n" +
            "                \"occupation\": \"\",\n" +
            "                \"registerTime\": 1459394732000,\n" +
            "                \"roleType\": 0,\n" +
            "                \"signature\": \"\",\n" +
            "                \"title\": \"青铜会员\",\n" +
            "                \"topicCount\": 1,\n" +
            "                \"totalExp\": 130,\n" +
            "                \"uid\": 0,\n" +
            "                \"userLevel\": 1,\n" +
            "                \"userNextLevel\": 2,\n" +
            "                \"username\": \"maoyan_movie\",\n" +
            "                \"vipInfo\": \"官方\",\n" +
            "                \"vipType\": 1,\n" +
            "                \"visitorCount\": 41\n" +
            "            },\n" +
            "            \"viewCount\": 11129\n" +
            "        }, {\n" +
            "            \"commentCount\": 6,\n" +
            "            \"description\": \"\",\n" +
            "            \"feedType\": 7,\n" +
            "            \"id\": 54971,\n" +
            "            \"images\": [{\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 1000,\n" +
            "                \"id\": 3162904,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34214,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p1.meituan" +
            ".net/movie/362081f92663f50cc0b70ee32f31864d933888.jpg@300w_225h_1e_1c\",\n" +
            "                \"width\": 1000\n" +
            "            }],\n" +
            "            \"latestFeedTime\": 0,\n" +
            "            \"rank\": 0,\n" +
            "            \"style\": 2,\n" +
            "            \"time\": 1518168540000,\n" +
            "            \"title\": \"《奇迹男孩》推迟下映，连续两周末上座率第一，口碑狂升\",\n" +
            "            \"upCount\": 11,\n" +
            "            \"updated\": 1518171921000,\n" +
            "            \"url\": \"meituanmovie://www.meituan.com/forum/newsDetail?id=34214\",\n" +
            "            \"user\": {\n" +
            "                \"age\": \"25\",\n" +
            "                \"authInfo\": \"\",\n" +
            "                \"avatarType\": 255,\n" +
            "                \"avatarurl\": \"https://img.meituan" +
            ".net/avatar/1fae1268d70810edf668395dc7a74bfc18495.jpg\",\n" +
            "                \"birthday\": -2240553957000,\n" +
            "                \"city\": {\n" +
            "                    \"deleted\": false,\n" +
            "                    \"id\": 1,\n" +
            "                    \"nm\": \"北京\",\n" +
            "                    \"py\": \"beijing\"\n" +
            "                },\n" +
            "                \"currentExp\": 0,\n" +
            "                \"gender\": 0,\n" +
            "                \"id\": 332456130,\n" +
            "                \"interest\": \"\",\n" +
            "                \"juryLevel\": 0,\n" +
            "                \"maoyanAge\": \"1.5\",\n" +
            "                \"marriage\": \"\",\n" +
            "                \"nextTitle\": \"\",\n" +
            "                \"nickName\": \"猫眼资讯\",\n" +
            "                \"occupation\": \"\",\n" +
            "                \"registerTime\": 1459394732000,\n" +
            "                \"roleType\": 0,\n" +
            "                \"signature\": \"\",\n" +
            "                \"title\": \"青铜会员\",\n" +
            "                \"topicCount\": 1,\n" +
            "                \"totalExp\": 130,\n" +
            "                \"uid\": 0,\n" +
            "                \"userLevel\": 1,\n" +
            "                \"userNextLevel\": 2,\n" +
            "                \"username\": \"maoyan_movie\",\n" +
            "                \"vipInfo\": \"官方\",\n" +
            "                \"vipType\": 1,\n" +
            "                \"visitorCount\": 41\n" +
            "            },\n" +
            "            \"viewCount\": 8817\n" +
            "        }, {\n" +
            "            \"description\": \"\",\n" +
            "            \"feedType\": 1,\n" +
            "            \"id\": 54970,\n" +
            "            \"images\": [{\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 247,\n" +
            "                \"id\": 3162900,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 54970,\n" +
            "                \"targetType\": 11,\n" +
            "                \"url\": \"http://p1.meituan" +
            ".net/movie/1024b26e4c9dc394bff6165a078c24522306889.gif@300w_225h_1e_1c_1l\",\n" +
            "                \"width\": 450\n" +
            "            }],\n" +
            "            \"latestFeedTime\": 0,\n" +
            "            \"rank\": 0,\n" +
            "            \"style\": 2,\n" +
            "            \"time\": 1518168000000,\n" +
            "            \"title\": \"问答精选：怎么看电影里的爱情故事总在国外发生\",\n" +
            "            \"updated\": 1518168342000,\n" +
            "            \"url\": \"meituanmovie://www.meituan.com/web?url=http://m.maoyan" +
            ".com/qanda/question/3413690?_v_=yes\",\n" +
            "            \"user\": {\n" +
            "                \"age\": \"28\",\n" +
            "                \"authInfo\": \"\",\n" +
            "                \"avatarType\": 255,\n" +
            "                \"avatarurl\": \"https://img.meituan" +
            ".net/avatar/7bc3228632209628efae704c1b4a733183123.png\",\n" +
            "                \"birthday\": 631123200000,\n" +
            "                \"currentExp\": 10,\n" +
            "                \"gender\": 0,\n" +
            "                \"id\": 1000863978,\n" +
            "                \"interest\": \"\",\n" +
            "                \"juryLevel\": 0,\n" +
            "                \"maoyanAge\": \"0\",\n" +
            "                \"marriage\": \"\",\n" +
            "                \"nextTitle\": \"\",\n" +
            "                \"nickName\": \"猫眼问答\",\n" +
            "                \"occupation\": \"\",\n" +
            "                \"registerTime\": 1505300082000,\n" +
            "                \"roleType\": 0,\n" +
            "                \"signature\": \"\",\n" +
            "                \"title\": \"青铜会员\",\n" +
            "                \"topicCount\": 7,\n" +
            "                \"totalExp\": 130,\n" +
            "                \"uid\": 0,\n" +
            "                \"userLevel\": 1,\n" +
            "                \"userNextLevel\": 2,\n" +
            "                \"username\": \"猫眼问答\",\n" +
            "                \"vipInfo\": \"\",\n" +
            "                \"vipType\": 0,\n" +
            "                \"visitorCount\": 987\n" +
            "            }\n" +
            "        }, {\n" +
            "            \"commentCount\": 5,\n" +
            "            \"description\": \"\",\n" +
            "            \"feedType\": 7,\n" +
            "            \"id\": 54968,\n" +
            "            \"images\": [{\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 420,\n" +
            "                \"id\": 3162892,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34212,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p0.meituan" +
            ".net/movie/475c3d519779d04f26a5d5397d894793268111.png@300w_225h_1e_1c\",\n" +
            "                \"width\": 420\n" +
            "            }, {\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 435,\n" +
            "                \"id\": 3162893,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34212,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p0.meituan" +
            ".net/movie/b6a22f6b70f0b329871e6465e7c6d018226384.png@300w_225h_1e_1c\",\n" +
            "                \"width\": 435\n" +
            "            }, {\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 371,\n" +
            "                \"id\": 3162895,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34212,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p0.meituan" +
            ".net/movie/6d951c5e32e487b4fc1d739662abc525398487.png@300w_225h_1e_1c\",\n" +
            "                \"width\": 371\n" +
            "            }],\n" +
            "            \"latestFeedTime\": 0,\n" +
            "            \"rank\": 0,\n" +
            "            \"style\": 3,\n" +
            "            \"time\": 1518165300000,\n" +
            "            \"title\": \"甜茶出演新片《国王》，演绎霸气君主亨利五世，挑战抖森荧幕形象\",\n" +
            "            \"upCount\": 7,\n" +
            "            \"updated\": 1518165317000,\n" +
            "            \"url\": \"meituanmovie://www.meituan.com/forum/newsDetail?id=34212\",\n" +
            "            \"user\": {\n" +
            "                \"age\": \"25\",\n" +
            "                \"avatarType\": 0,\n" +
            "                \"avatarurl\": \"\",\n" +
            "                \"birthday\": -2240553957000,\n" +
            "                \"currentExp\": 0,\n" +
            "                \"gender\": 0,\n" +
            "                \"id\": 976532152,\n" +
            "                \"juryLevel\": 0,\n" +
            "                \"maoyanAge\": \"0\",\n" +
            "                \"nextTitle\": \"\",\n" +
            "                \"nickName\": \"猫眼娱乐\",\n" +
            "                \"registerTime\": 1503568355000,\n" +
            "                \"roleType\": 0,\n" +
            "                \"title\": \"青铜会员\",\n" +
            "                \"topicCount\": 2,\n" +
            "                \"totalExp\": 130,\n" +
            "                \"uid\": 0,\n" +
            "                \"userLevel\": 1,\n" +
            "                \"userNextLevel\": 2,\n" +
            "                \"username\": \"猫眼娱乐\",\n" +
            "                \"vipType\": 0,\n" +
            "                \"visitorCount\": 0\n" +
            "            },\n" +
            "            \"viewCount\": 8017\n" +
            "        }, {\n" +
            "            \"commentCount\": 2,\n" +
            "            \"description\": \"\",\n" +
            "            \"feedType\": 7,\n" +
            "            \"id\": 54966,\n" +
            "            \"images\": [{\n" +
            "                \"authorId\": 0,\n" +
            "                \"height\": 562,\n" +
            "                \"id\": 3162890,\n" +
            "                \"sizeType\": 0,\n" +
            "                \"targetId\": 34211,\n" +
            "                \"targetType\": 13,\n" +
            "                \"url\": \"http://p0.meituan" +
            ".net/movie/d9354568663672513045622e6488653f204440.jpg@300w_225h_1e_1c\",\n" +
            "                \"width\": 562\n" +
            "            }],\n" +
            "            \"latestFeedTime\": 0,\n" +
            "            \"rank\": 0,\n" +
            "            \"style\": 2,\n" +
            "            \"time\": 1518165060000,\n" +
            "            \"title\": \"迪丽热巴要演拜金女！嗜包如命花钱不手软，惨遇“抠门”郭京飞\",\n" +
            "            \"upCount\": 6,\n" +
            "            \"updated\": 1518165039000,\n" +
            "            \"url\": \"meituanmovie://www.meituan.com/forum/newsDetail?id=34211\",\n" +
            "            \"user\": {\n" +
            "                \"age\": \"25\",\n" +
            "                \"authInfo\": \"\",\n" +
            "                \"avatarType\": 255,\n" +
            "                \"avatarurl\": \"https://img.meituan" +
            ".net/avatar/1fae1268d70810edf668395dc7a74bfc18495.jpg\",\n" +
            "                \"birthday\": -2240553957000,\n" +
            "                \"city\": {\n" +
            "                    \"deleted\": false,\n" +
            "                    \"id\": 1,\n" +
            "                    \"nm\": \"北京\",\n" +
            "                    \"py\": \"beijing\"\n" +
            "                },\n" +
            "                \"currentExp\": 0,\n" +
            "                \"gender\": 0,\n" +
            "                \"id\": 332456130,\n" +
            "                \"interest\": \"\",\n" +
            "                \"juryLevel\": 0,\n" +
            "                \"maoyanAge\": \"1.5\",\n" +
            "                \"marriage\": \"\",\n" +
            "                \"nextTitle\": \"\",\n" +
            "                \"nickName\": \"猫眼资讯\",\n" +
            "                \"occupation\": \"\",\n" +
            "                \"registerTime\": 1459394732000,\n" +
            "                \"roleType\": 0,\n" +
            "                \"signature\": \"\",\n" +
            "                \"title\": \"青铜会员\",\n" +
            "                \"topicCount\": 1,\n" +
            "                \"totalExp\": 130,\n" +
            "                \"uid\": 0,\n" +
            "                \"userLevel\": 1,\n" +
            "                \"userNextLevel\": 2,\n" +
            "                \"username\": \"maoyan_movie\",\n" +
            "                \"vipInfo\": \"官方\",\n" +
            "                \"vipType\": 1,\n" +
            "                \"visitorCount\": 41\n" +
            "            },\n" +
            "            \"viewCount\": 6264\n" +
            "        }],\n" +
            "        \"paging\": {\n" +
            "            \"hasMore\": true,\n" +
            "            \"limit\": 10,\n" +
            "            \"offset\": 0,\n" +
            "            \"total\": 0\n" +
            "        },\n" +
            "        \"timestamp\": 1518186584239\n" +
            "    }";

    public DiscoverPresenter(Activity activity, DiscoverContract.IDiscoverView view) {
        super(activity, view);
        discoverManager = new DiscoverManager();
    }

    @Override
    public void getDiscoverData(int offset, int limit) {
        mView.showLoading();
//        addSubscribeUntilDestroy(discoverManager.getDiscoverData(offset, limit)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<DiscoverBean>() {
//                    @Override
//                    public void accept( DiscoverBean discoverBean)  {
//                        mView.addDiscoverData(discoverBean.getData().getFeeds());
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable)  {
//                        mView.showError(ExceptionHandle.handleException(throwable));
//
//                    }
//                }, new Action() {
//                    @Override
//                    public void run()  {
//                        mView.showContent();
//                    }
//                }));
    
    
        DiscoverBean.DataBean temp = GsonUtil.getGson().fromJson(mock_json, DiscoverBean.DataBean.class);
        mView.addDiscoverData(temp.getFeeds());
        mView.showContent();
    }

    @Override
    public void getDiscoverHeader(String utm_term) {
        addSubscribeUntilDestroy(discoverManager.getDiscoverHeaderData(utm_term)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DiscoverHeaderBean>() {
                    @Override
                    public void accept(@NonNull DiscoverHeaderBean discoverHeaderBean) throws Exception {
                        mView.addDiscoverHeaderData(discoverHeaderBean.getData());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showError("获取顶部栏目失败");
                    }
                }));
    }
}
