package com.example.takahiro.alarmapp;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.h2.Driver;
import org.h2.util.JdbcUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;             //手動でimport
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;                       //手動でimport

/**
 * Created by Takahiro on 2016/05/15.
 */
@RunWith(Enclosed.class)
public class UserDaoTest {

    public static class インメモリでのDB起動確認 {
        Connection conn = null;
        Statement st1 = null;

        @Before
        public void setUp() throws Exception{
            // インメモリでDB起動
            Driver.load();
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"); // DB_CLOSE_DELAYオプション付き(コネクション切れないように)
            // スキーマ作成
            try {
                conn.createStatement().execute("CREATE SCHEMA IF NOT EXISTS " + "test");
            } finally {                                                             //スキーマ名
                JdbcUtils.closeSilently(conn);
            }
        }

        @Test
        public void getListで2件取得できること() throws Exception{
            st1 = conn.createStatement();
            st1.execute("create table test (id int primary key, name varchar)");
            st1.execute("insert into test values (1, 'hoge')");
            ResultSet rs1 = st1.executeQuery("select * from test");
            if(rs1.next()){
                System.out.println("DB_インメモリ起動確認");
            }
        }

        @After
        public void tearDown() {
            try {
                st1.close();  // ステートメントをクローズ
                conn.close(); // コネクションをクローズ
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

/*        @ClassRule
        public static H2DatabaseServer sever = new H2DatabaseServer();*/

/*        @Rule
        public DbUnitTester tester = new UserDaoDbUNitTeser();
//                new UserDaoDbUNitTeser("C:\\\\Users\\\\Takahiro\\\\Documents\\\\h2dataBaseTest\\\\fixtures.xml");*/

//        HistActivity sut;

/*        @Before
        public void setUp() throws Exception{
            this.sut = new HistActivity();
        }*/

/*        @Test
        public void getListで2件取得できること() throws Exception{
            //Exercise
            ArrayList<RegisterBean> actual = sut.getList();
            //Verify
            assertThat(actual, is(notNullValue()));
            assertThat(actual.size(), is(2));
            assertThat(actual.get(0).getRegisterId(), is(1));
            assertThat(actual.get(1).getRegisterId(), is(2));
        }*/
    }

/*    static class UserDaoDbUNitTeser extends DbUnitTester{
        //private final String fixture;

*//*        public UserDaoDbUNitTeser(String fixture){
            this.fixture = fixture;
        }*//*

        @Override
        protected void before() throws Exception{
            executeQuery("DROP TABLE IF EXISTS users");
            executeQuery("CREATE TABLE users(id INT AUTO_INCREMENT, name VARCHAR(64))");
        }

        @Override
        protected IDataSet createDataSet() throws Exception {
            return  new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("da"));
        }
    }*/
}
