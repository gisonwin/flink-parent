package com.gisonwin.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 11:24
 */
public class HBaseUtils {

    private static Admin admin = null;
    private static Connection conn = null;

    static {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://192.168.1.113:9000/hbase");
        conf.set("hbase.zookeeper.quorum", "192.168.1.113");
        conf.set("hbase.client.scanner.timeout.period", "600000");
        conf.set("hbase.rpc.timeout", "600000");
        try {
            conn = ConnectionFactory.createConnection(conf);
            //get admin manager
            admin = conn.getAdmin();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /***
     * create table
     * @param tabName
     * @param familyName
     * @throws Exception
     */
    public void createTable(String tabName, String familyName) throws Exception {
        ColumnFamilyDescriptor columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(familyName.getBytes()).build();
        TableDescriptor build = TableDescriptorBuilder.newBuilder(TableName.valueOf(tabName)).setColumnFamily(columnFamilyDescriptorBuilder).build();
        admin.createTable(build);

    }

    /**
     * @param tablename
     * @param rowkey
     * @param familyname
     * @param datamap
     * @throws Exception
     */
    public static void put(String tablename, String rowkey, String familyname, Map<String, String> datamap) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tablename));
        byte[] rowkeyByte = Bytes.toBytes(rowkey);
        Put put = new Put(rowkeyByte);
        if (datamap != null) {
            Set<Map.Entry<String, String>> set = datamap.entrySet();
            for (Map.Entry<String, String> entry : set) {
                String key = entry.getKey();
                String value = entry.getValue();
                put.addColumn(Bytes.toBytes(familyname), Bytes.toBytes(key), Bytes.toBytes(value));
            }

        }
        table.put(put);
        table.close();
        System.out.println("ok");
    }

    public static String getdata(String tablename, String rowkey, String familyname, String column) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tablename));
        byte[] bytes = Bytes.toBytes(rowkey);
        Get get = new Get(bytes);

        Result result = table.get(get);
        byte[] value = result.getValue(familyname.getBytes(), column.getBytes());
        if (value == null) {
            return null;
        }
        return new String(value);
    }

    public static void putdata(String tablename,String rowkey,String familyname,String colum,String data) throws Exception{
        Table table = conn.getTable(TableName.valueOf(tablename));
        Put put = new Put(rowkey.getBytes());
        put.addColumn(familyname.getBytes(), colum.getBytes(), data.getBytes());
        table.put(put);

    }
}
