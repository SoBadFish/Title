package org.sobadfish.title.manager;

import cn.nukkit.utils.Config;
import com.smallaswater.easysql.mysql.data.SqlData;
import com.smallaswater.easysql.mysql.data.SqlDataList;
import com.smallaswater.easysql.mysql.utils.ChunkSqlType;
import com.smallaswater.easysql.mysql.utils.DataType;
import com.smallaswater.easysql.mysql.utils.TableType;
import com.smallaswater.easysql.mysql.utils.UserData;
import com.smallaswater.easysql.v3.mysql.manager.SqlManager;
import com.smallaswater.easysql.v3.mysql.utils.SelectType;
import org.sobadfish.title.TitleMain;
import org.sobadfish.title.data.PlayerData;
import org.sobadfish.title.data.TitleData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Sobadfish
 * @date 2022/9/15
 */
public class PlayerManager implements IDataManager{

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    public SqlManager sqlManager;

    public boolean connected;

    private static final String PLAYER_TABLE = "player";

    private static final String TITLE_TABLE = "title";

    //本地缓存..
    public List<PlayerData> dataList;

    private PlayerManager(List<PlayerData> dataList,SqlManager sqlManager) {
        this.dataList = dataList;
        this.sqlManager = sqlManager;

    }

    @Override
    public PlayerData getData(String player){
        PlayerData data = new PlayerData(player);
        if(dataList.contains(data)){
            return dataList.get(dataList.indexOf(data));
        }
        if(connected){
            SqlDataList<SqlData> sqlDataList = sqlManager.getData("SELECT * FROM "+PLAYER_TABLE+" LEFT JOIN "+TITLE_TABLE+" ON user = id  WHERE player=?"
                    ,new ChunkSqlType(1,player));
            if(sqlDataList.size() > 0){
                for(SqlData titleData: sqlDataList){
                    String time = titleData.getString("time");
                    if(time.isEmpty() || "null".equalsIgnoreCase(time)){
                        time = null;
                    }
                    TitleData td = new TitleData(
                            titleData.getString("name"),
                            time,
                            titleData.getString("cmd"),
                            titleData.getInt("delay")
                    );
                    td.id =  titleData.getInt("tid");
                    data.titles.add(td);
                    if((td.id+"").equalsIgnoreCase(titleData.getString("wear"))){
                        data.wearTitle(td);
                    }
                }

                dataList.add(data);

            }


        }
        return data;
    }

    @Override
    public void addTitle(String player, TitleData titleData){
        //后台执行就可以了
        EXECUTOR_SERVICE.execute(() -> {
            if (connected) {

                if (!sqlManager.isExistsData(PLAYER_TABLE, "player", player)) {
                    //判断玩家是否在数据表 没有则添加
                    sqlManager.insertData(PLAYER_TABLE, new SqlData("player", player).put("wear", ""));
                }
                SqlDataList<SqlData> sqlDataList = sqlManager.getData("SELECT * FROM " + PLAYER_TABLE + " WHERE player = ?"
                        , new ChunkSqlType(1, player));
                SqlData sqlData = sqlDataList.get();

                SqlData sd = new SqlData();
                sd.put("name", titleData.name);
                sd.put("cmd", titleData.cmd);
                sd.put("delay", titleData.delay);
                String time = titleData.outTime;
                if(titleData.outTime == null || "null".equalsIgnoreCase(titleData.outTime)){
                    time = "";
                }
                sd.put("time", time);


                //重写判断SQL
                //先判断称号是否存在 存在则更新
                String sql = "WHERE name = ? AND user = ?";
                if(sqlManager.getDataSize( sql,TITLE_TABLE, new ChunkSqlType(1, titleData.name)) > 0){
                    sqlManager.setData(TITLE_TABLE, sd, new SqlData("name", titleData.name).put("user",sqlData.getInt("id")));
                }else{
                    sd.put("user", sqlData.getInt("id"));
                    sqlManager.insertData(TITLE_TABLE, sd);
                }

                //查询到 新增的title
                SqlDataList<SqlData> titleDb = sqlManager.getData("SELECT tid FROM " + TITLE_TABLE + " WHERE name = ? AND user = ?"
                        , new ChunkSqlType(1, titleData.name), new ChunkSqlType(2, sqlData.getInt("id") + ""));
                if (titleDb.size() > 0) {
                    SqlData tdb = titleDb.get();
                    sqlManager.setData(PLAYER_TABLE, new SqlData("wear", tdb.getInt("tid")), new SqlData("id",
                            sqlData.getInt("id")));
                }
            }
        });

    }

    @Override
    public void removeTitle(String player, String titleData){
        EXECUTOR_SERVICE.execute(() -> {
            if(connected) {
                SqlDataList<SqlData> sqlDataList = sqlManager.getData("SELECT tid FROM "
                                +PLAYER_TABLE+" LEFT JOIN "
                                +TITLE_TABLE+" ON user = id  WHERE player=? AND name = ?"
                        ,new ChunkSqlType(1,player),new ChunkSqlType(2,titleData));

                if(sqlDataList.size() > 0){
                    sqlManager.deleteData(TITLE_TABLE,sqlDataList.get());
                }

            }
        });
    }

//    public static PlayerManager asFile(File file){
//        return (PlayerManager) BaseDataWriterGetterManager.asFile(file,"player.json", PlayerData[].class,PlayerManager.class);
//    }

    public static PlayerManager asDb(TitleMain titleMain) {
        Config config = titleMain.getConfig();


//        //TODO 获取数据库内的数据
        try {


            SqlManager sqlDataManager = new SqlManager(titleMain,new UserData(
                    config.getString("mysql.user"),
                    config.getString("mysql.password"),
                    config.getString("mysql.host"),
                    config.getInt("mysql.port"),
                    config.getString("mysql.database")

                    ));
            PlayerManager playerManager = new PlayerManager(new ArrayList<>(),sqlDataManager);
            playerManager.connected = true;
            playerManager.init();
            return playerManager;
//
        } catch (Exception e) {
            e.printStackTrace();
            titleMain.getLogger().error("无法连接到数据库！ 请检查MySQL参数设置是否有误！");
        }
        return new PlayerManager(new ArrayList<>(),null);
    }

    private void init() {
        //TODO 初始化MySQL参数

        sqlManager.createTable(PLAYER_TABLE, new TableType(
                "id", DataType.getID()),
                new TableType("player", DataType.getVARCHAR()),
                new TableType("wear", DataType.getVARCHAR())
                );
        sqlManager.createTable(TITLE_TABLE, new TableType(
                "tid", DataType.getID()),
                new TableType("name", DataType.getVARCHAR()),
                new TableType("cmd", DataType.getVARCHAR()),
                new TableType("delay", DataType.getINT()),
                new TableType("time", DataType.getVARCHAR()),
                new TableType("user", DataType.getINT()));

    }



    @Override
    public void wearTitle(String player, TitleData title) {
        if(connected){
            EXECUTOR_SERVICE.execute(() -> {
                //修改称号
                if (!sqlManager.isExistsData(PLAYER_TABLE, "player", player)) {
                    //判断玩家是否在数据表 没有则添加
                    sqlManager.insertData(PLAYER_TABLE, new SqlData("player", player).put("wear",""));
                }
                if(title != null){
                    int userId = sqlManager.getData(PLAYER_TABLE, new SelectType("player",player)).get().getInt("id");
                    SqlDataList<SqlData> sqlDataList = sqlManager.getData("SELECT * FROM " + TITLE_TABLE + " WHERE name = ? AND user = ?"
                            , new ChunkSqlType(1, title.name),new ChunkSqlType(2,userId+""));

                    if(sqlDataList.size() > 0){
                        SqlData titleData = sqlDataList.get();
                        int titleId = titleData.getInt("tid");
                        sqlManager.setData(PLAYER_TABLE, new SqlData("wear",titleId),new SqlData("player",player));
                    }
                }else{
                    sqlManager.setData(PLAYER_TABLE, new SqlData("wear",-1),new SqlData("player",player));
                }

            });
        }

    }

    @Override
    public List<PlayerData> getDataList() {
        return dataList;
    }
}
