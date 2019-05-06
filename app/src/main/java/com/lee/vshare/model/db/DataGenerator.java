/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lee.vshare.model.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    private static final String[] FIRST = new String[]{
            "Special edition", "New", "Cheap", "Quality", "Used"};
    private static final String[] SECOND = new String[]{
            "Three-headed Monkey", "Rubber Chicken", "Pint of Grog", "Monocle"};
    private static final String[] DESCRIPTION = new String[]{
            "is finally here", "is recommended by Stan S. Stanman",
            "is the best sold product on Mêlée Island", "is \uD83D\uDCAF", "is ❤️", "is fine"};
    private static final String[] COMMENTS = new String[]{
            "Comment 1", "Comment 2", "Comment 3", "Comment 4", "Comment 5", "Comment 6"};

    public static List<com.lee.vshare.model.db.entity.LoginHistoryEntity> generateLoginHistory() {
        List<com.lee.vshare.model.db.entity.LoginHistoryEntity> histories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            com.lee.vshare.model.db.entity.LoginHistoryEntity history = new com.lee.vshare.model.db.entity.LoginHistoryEntity();
            history.setUserId(i);
            history.setUserName("name" + i);
            history.setPassword("password" + i);
            history.setUserImgUrl("imgUrl" + i);
            history.setLoginDate(new Date());
            histories.add(history);
        }
        return histories;
    }

    public static List<com.lee.vshare.model.db.entity.LoginHistoryEntity> generateLoginHistory(final List<com.lee.vshare.model.db.entity.ex.LoginHistory> loginHistories) {
        List<com.lee.vshare.model.db.entity.LoginHistoryEntity> histories = new ArrayList<>();
//        for (LoginHistoryEntity history : histories) {
//            LoginHistoryEntity comment = new LoginHistoryEntity();
//            comment.setProductId(product.getId());
//            comment.setText(COMMENTS[i] + " for " + product.getName());
//            comment.setPostedAt(new Date(System.currentTimeMillis()
//                    - TimeUnit.DAYS.toMillis(commentsNumber - i) + TimeUnit.HOURS.toMillis(i)));
//            histories.add(comment);
//        }

        return histories;
    }
}
