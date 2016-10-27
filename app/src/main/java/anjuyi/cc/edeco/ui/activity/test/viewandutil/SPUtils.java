package anjuyi.cc.edeco.ui.activity.test.viewandutil;

import android.content.Context;
import android.content.SharedPreferences;

import anjuyi.cc.edeco.https.Const;

/**
 * Created by ly on 2016/5/25 16:27.
 * Sharepreference数据存储工具类
 */
public class SPUtils {
//    /**
//     * 对SharedPreference的使用做了建议的封装，对外公布出put，get，remove，clear等等方法；
//     注意一点，里面所有的commit操作使用了SharedPreferencesCompat.apply进行了替代，目的是尽可能的使用apply代替commit
//     首先说下为什么，因为commit方法是同步的，并且我们很多时候的commit操作都是UI线程中，毕竟是IO操作，尽可能异步；
//     所以我们使用apply进行替代，apply异步的进行写入；
//     但是apply相当于commit来说是new API呢，为了更好的兼容，我们做了适配；
//     */
//
//    /**
//     * 保存在手机里面的文件名
//     */
//    public static final String FILE_NAME = "share_data";
//
//    /**
//     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
//     */
//    public static void put(Context context, String key, Object object) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//
//        if (object instanceof String) {
//            editor.putString(key, (String) object);
//        } else if (object instanceof Integer) {
//            editor.putInt(key, (Integer) object);
//        } else if (object instanceof Boolean) {
//            editor.putBoolean(key, (Boolean) object);
//        } else if (object instanceof Float) {
//            editor.putFloat(key, (Float) object);
//        } else if (object instanceof Long) {
//            editor.putLong(key, (Long) object);
//        } else {
//            editor.putString(key, object.toString());
//        }
//
//        SharedPreferencesCompat.apply(editor);
//    }
//
//    /**
//     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
//     */
//    public static Object get(Context context, String key, Object defaultObject) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//
//        if (defaultObject instanceof String) {
//            return sp.getString(key, (String) defaultObject);
//        } else if (defaultObject instanceof Integer) {
//            return sp.getInt(key, (Integer) defaultObject);
//        } else if (defaultObject instanceof Boolean) {
//            return sp.getBoolean(key, (Boolean) defaultObject);
//        } else if (defaultObject instanceof Float) {
//            return sp.getFloat(key, (Float) defaultObject);
//        } else if (defaultObject instanceof Long) {
//            return sp.getLong(key, (Long) defaultObject);
//        }
//        return null;
//    }
//
//    /**
//     * 移除某个key值已经对应的值
//     */
//    public static void remove(Context context, String key) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.remove(key);
//        SharedPreferencesCompat.apply(editor);
//    }
//
//    /**
//     * 清除所有数据
//     */
//    public static void clear(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.clear();
//        SharedPreferencesCompat.apply(editor);
//    }
//
//    /**
//     * 查询某个key是否已经存在
//     */
//    public static boolean contains(Context context, String key) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//        return sp.contains(key);
//    }
//
//    /**
//     * 返回所有的键值对
//     */
//    public static Map<String, ?> getAll(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//        return sp.getAll();
//    }
//
//    /**
//     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
//     */
//    private static class SharedPreferencesCompat {
//        private static final Method sApplyMethod = findApplyMethod();
//
//        /**
//         * 反射查找apply的方法
//         */
//        @SuppressWarnings({"unchecked", "rawtypes"})
//        private static Method findApplyMethod() {
//            try {
//                Class clz = SharedPreferences.Editor.class;
//                return clz.getMethod("apply");
//            } catch (NoSuchMethodException e) {
//            }
//
//            return null;
//        }
//
//        /**
//         * 如果找到则使用apply执行，否则使用commit
//         */
//        public static void apply(SharedPreferences.Editor editor) {
//            try {
//                if (sApplyMethod != null) {
//                    sApplyMethod.invoke(editor);
//                    return;
//                }
//            } catch (IllegalArgumentException e) {
//            } catch (IllegalAccessException e) {
//            } catch (InvocationTargetException e) {
//            }
//            editor.commit();
//        }
//    }

    //==============================================================================================================================
    private SPUtils() {
    }

    private static SharedPreferences mPres;
    private static SPUtils mInstance;

    public void init(Context context) {
        mPres = context.getSharedPreferences(Const.SHARE_NAME,
                Context.MODE_PRIVATE);
    }

    public void clear() {
        SharedPreferences.Editor edit = mPres.edit();
        edit.clear();
        edit.apply();
    };

    public static SPUtils getInstance() {
        if(mInstance==null){
            synchronized (SPUtils.class) {
                if(mInstance==null){
                    mInstance=new SPUtils();
                }
            }
        }
        return mInstance;
    }

    public String getString(String key) {
        return mPres.getString(key, "");
    }

    public int getInt(String key) {
        return mPres.getInt(key, -1);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor edit = mPres.edit();
        edit.putLong(key, value);
        edit.apply();
    }

    public long getLong(String key) {
        return mPres.getLong(key, -1);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor edit = mPres.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor edit = mPres.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor edit = mPres.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public boolean getBoolean(String key, boolean value) {
        return mPres.getBoolean(key, value);
    }

    public boolean getBoolean(String key) {
        return mPres.getBoolean(key, false);
    }
}
