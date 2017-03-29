package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 作者：Mr.Lee on 2016-11-14 09:53
 * 邮箱：569932357@qq.com
 */

public class DaoBeautyDb {

     public static void main(String[] args) throws Exception {
        // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
        Schema schema = new Schema(1, "beautycache");
         // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
         Entity note = schema.addEntity("Beauty");
         note.addIdProperty();
         note.addStringProperty("createdAt").notNull();
         note.addStringProperty("desc");
         note.addStringProperty("publishedAt");
         note.addStringProperty("source");
         note.addStringProperty("type");
         note.addStringProperty("url");
         note.addStringProperty("used");
         note.addStringProperty("who");
        new DaoGenerator().generateAll(schema, "d:/code/trunk/app/src/main/java-gen");
    }

}
