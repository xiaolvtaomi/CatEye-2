package com.cicinnus.cateye.module.movie.find_movie.awards_movie.awards_list;

import android.app.Activity;

import com.cicinnus.cateye.tools.GsonUtil;
import com.cicinnus.retrofitlib.net.error.ExceptionHandle;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/2/7.
 */

public class AwardsListMVPPresenter extends com.cicinnus.retrofitlib.base.BaseMVPPresenter<AwardsListContract.IAwardsListView> implements AwardsListContract.IAwardsListPresenter {

    private final AwardsListManager awardsListManager;
    
    private final String mock_json = " [{\n" +
            "        \"festivals\": [{\n" +
            "            \"festivalId\": 117,\n" +
            "            \"festivalName\": \"信息与计算科学\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 116,\n" +
            "            \"festivalName\": \"计算数学及其应用软件\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 115,\n" +
            "            \"festivalName\": \"信息科学\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 114,\n" +
            "            \"festivalName\": \"网络工程\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 113,\n" +
            "            \"festivalName\": \"计算机与自动检测\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 112,\n" +
            "            \"festivalName\": \"计算机应用及安全管理\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 111,\n" +
            "            \"festivalName\": \"网络与信息安全\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 110,\n" +
            "            \"festivalName\": \"信息安全\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 83,\n" +
            "            \"festivalName\": \"微电子学\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 109,\n" +
            "            \"festivalName\": \"电子信息科学与技术\"\n" +
            "        }],\n" +
            "        \"region\": \"计算机学院\"\n" +
            "    }, {\n" +
            "        \"festivals\": [{\n" +
            "            \"festivalId\": 117,\n" +
            "            \"festivalName\": \"信息与计算科学\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 116,\n" +
            "            \"festivalName\": \"计算数学及其应用软件\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 115,\n" +
            "            \"festivalName\": \"信息科学\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 114,\n" +
            "            \"festivalName\": \"网络工程\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 113,\n" +
            "            \"festivalName\": \"计算机与自动检测\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 112,\n" +
            "            \"festivalName\": \"计算机应用及安全管理\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 111,\n" +
            "            \"festivalName\": \"网络与信息安全\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 110,\n" +
            "            \"festivalName\": \"信息安全\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 83,\n" +
            "            \"festivalName\": \"微电子学\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 109,\n" +
            "            \"festivalName\": \"电子信息科学与技术\"\n" +
            "        }],\n" +
            "        \"region\": \"计算机学院\"\n" +
            "    }, {\n" +
            "        \"festivals\": [{\n" +
            "            \"festivalId\": 117,\n" +
            "            \"festivalName\": \"信息与计算科学\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 116,\n" +
            "            \"festivalName\": \"计算数学及其应用软件\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 115,\n" +
            "            \"festivalName\": \"信息科学\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 114,\n" +
            "            \"festivalName\": \"网络工程\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 113,\n" +
            "            \"festivalName\": \"计算机与自动检测\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 112,\n" +
            "            \"festivalName\": \"计算机应用及安全管理\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 111,\n" +
            "            \"festivalName\": \"网络与信息安全\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 110,\n" +
            "            \"festivalName\": \"信息安全\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 83,\n" +
            "            \"festivalName\": \"微电子学\"\n" +
            "        }, {\n" +
            "            \"festivalId\": 109,\n" +
            "            \"festivalName\": \"电子信息科学与技术\"\n" +
            "        }],\n" +
            "        \"region\": \"计算机学院\"\n" +
            "    }]";

    public AwardsListMVPPresenter(Activity activity, AwardsListContract.IAwardsListView view) {
        super(activity, view);
        awardsListManager = new AwardsListManager();
    }

    @Override
    public void getAwardsList() {
        mView.showLoading();
//        addSubscribeUntilDestroy(awardsListManager.getAwardsList()
//                .subscribe(new Consumer<AwardsListBean>() {
//                    @Override
//                    public void accept(@NonNull AwardsListBean awardsListBean) throws Exception {
//                        mView.addAwardsList(awardsListBean.getData());
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        mView.showError(ExceptionHandle.handleException(throwable));
//
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        mView.showContent();
//
//                    }
//                }));
    
    
        Type dataType = new TypeToken<List<AwardsListBean.DataBean>>(){}.getType();
        List<AwardsListBean.DataBean> data = GsonUtil.getGson().fromJson(mock_json, dataType);
        mView.addAwardsList(data);
        mView.showContent();
    }
}
