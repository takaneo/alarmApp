package com.example.takahiro.alarmapp;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;             //手動でimport
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;                       //手動でimport

/**
 * Created by Takahiro on 2016/05/15.
 */
@RunWith(Enclosed.class)
public class UserDaoTest {

    public static class usersに2件のレコードがある場合 {
        @ClassRule
        public static H2DatabaseServer sever =
                new H2DatabaseServer("C:\\\\Users\\\\Takahiro\\\\Documents\\\\h2dataBaseTest", "sqltest", "sc");

        @Rule
        public DbUnitTester tester =
                new UserDaoDbUNitTeser("C:\\\\Users\\\\Takahiro\\\\Documents\\\\h2dataBaseTest\\\\fixtures.xml");

        HistActivity sut;

        @Before
        public void setUp() throws Exception{
            this.sut = new HistActivity();
        }

        @Test
        public void getListで2件取得できること() throws Exception{
            //Exercise
            ArrayList<RegisterBean> actual = sut.getList();
            //Verify
            assertThat(actual, is(notNullValue()));
            assertThat(actual.size(), is(2));
            assertThat(actual.get(0).getRegisterId(), is(1));
            assertThat(actual.get(1).getRegisterId(), is(2));
        }
    }

    static class UserDaoDbUNitTeser extends DbUnitTester{
        private final String fixture;
        public UserDaoDbUNitTeser(String fixture){
//            super("org.h2.Driver", "jdbc:h2:tcp://localhost/db;SCHEMA=sc", "paparoach", "paparoach", "sc");
super("org.h2.Driver", "jdbc:h2:tcp://localhost/sqltest;SCHEMA=sc", "paparoach", "paparoach", "sc");
//            super("org.h2.Driver", "jdbc:h2:c:\\\\Users\\\\Takahiro\\\\Documents\\\\h2dataBaseTest\\\\sqltest", "paparoach", "paparoach", "sc");
            this.fixture = fixture;
        }

        @Override
        protected void before() throws Exception{
            executeQuery("DROP TABLE IF EXISTS users");
            executeQuery("CREATE TABLE users(id INT AUTO_INCREMENT, name VARCHAR(64))");
        }

        @Override
        protected IDataSet createDataSet() throws Exception {
            return  new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(fixture));
        }
    }
}
