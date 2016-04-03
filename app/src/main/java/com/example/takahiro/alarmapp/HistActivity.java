package com.example.takahiro.alarmapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HistActivity extends AppCompatActivity {
    static SQLiteDatabase mydb;
    ArrayList<RegisterBean> registerList = new ArrayList();  // インスタンス生成必須
    ArrayAdapter<String> adapter;
    ListView lview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Intent intent = getIntent(); 遷移元からデータ引き継いでないので記述不要
        MySQLiteOpenHelper hlpr = new MySQLiteOpenHelper(getApplicationContext());
        mydb = hlpr.getReadableDatabase();
        Cursor mCursor = mydb.rawQuery("select RegisterId, RegisterDate, ExecuteDate from alarmTalbe order by RegisterId desc", null);

        // 予約情報が1件もない場合_よい実装を考える。
        if (mCursor == null){
            return;
        }
        // DBから取得したデータをListに詰める
        while (mCursor.moveToNext()) {
            RegisterBean bean = new RegisterBean();
            bean.setRegisterId(mCursor.getInt(mCursor.getColumnIndex("RegisterId")));
            bean.setRegisterDate(mCursor.getString(mCursor.getColumnIndex("RegisterDate")));
            bean.setExecuteDate(mCursor.getString(mCursor.getColumnIndex("ExecuteDate")));
            bean.setRegisterInfo("予約No" + mCursor.getInt(mCursor.getColumnIndex("RegisterId")) + "："+ mCursor.getString(mCursor.getColumnIndex("ExecuteDate")));
            registerList.add(bean);
        }

        // ListView用に実行日だけ詰め替える
        final ArrayList<String> showList = new ArrayList<>();
        for (RegisterBean bean : registerList){
            showList.add(bean.getRegisterInfo());
        }

        lview = (ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, showList);
        lview.setAdapter(adapter);

        // リスト項目をクリック
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String deleteId; // onItemClickの中で宣言すると、ダイアログ生成のonClickで変数が使用できない
            int position = -1;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                ListView lv = (ListView) parent;

                deleteId = (String) lv.getItemAtPosition(pos);   //選択項目
                int index = deleteId.indexOf("：");
                deleteId = deleteId.substring(4, index);
                position = pos;

                // 確認ダイアログ生成
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(HistActivity.this);  // ここはgetApplicationContext()では動かない
                alertDlg.setTitle("削除確認");
                alertDlg.setMessage("指定した履歴を削除します。");
                alertDlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        mydb.rawQuery("Delete From alarmTalbe Where RegisterId = ?", new String[]{deleteId}).moveToFirst() ;
                        showList.remove(position);
                        adapter.notifyDataSetChanged();

                        // OK ボタンクリック処理
                        Toast.makeText(getApplicationContext(), "削除しました", Toast.LENGTH_LONG).show();

                        // ListViewからデータ削除
                        // adapter.remove(item);
                    }
                });
                alertDlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel ボタンクリック処理
                        Toast.makeText(getApplicationContext(), "Cancel押されました", Toast.LENGTH_LONG).show();
                    }
                });
                // 表示
                alertDlg.create().show();
            }
        });

        // 戻るボタン
        Button btn = (Button)this.findViewById(R.id.btnBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}


// debug用
/*          int eId = mCursor.getInt(mCursor.getColumnIndex("RegisterId"));
            String rDate = mCursor.getString(mCursor.getColumnIndex("RegisterDate"));
            String eDate = mCursor.getString(mCursor.getColumnIndex("ExecuteDate"));*/

/*        try {
            while (mCursor.moveToNext()) {
                String date = mCursor.getString(mCursor.getColumnIndex("RegisterDate"));
                rDates.add(date);
            }
        } finally {
            mCursor.close();
        }
*/

/*        Cursor mCursor = mydb.query("alarmTalbe",
                new String[] { "RegisterId", "RegisterDate", "ExecuteDate"}, null, null, null, null, null);*/
