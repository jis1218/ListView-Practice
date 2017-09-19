package com.example.basiclist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.basiclist.R.id.textView;

/*
리스트 사용하기
 */

public class MainActivity extends AppCompatActivity {

    // 1. 데이터를 정의
    ArrayList<String> data = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1. 데이터를 정의
        for(int i=0; i<100; i++){
            data.add("임시값 " + i);
        }
        // 2. 데이터와 리스트뷰를 연결하는 아답터를 생성
        CustomAdapter adapter = new CustomAdapter(this, data);

        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);

        // 3. 아답터와 리스트뷰를 연결
    }
}

//기본 아답터 클래스를 상속받아 구현
class CustomAdapter extends BaseAdapter{
    //데이터 저장소를 아답터 내부에 두는 것이 컨트롤 하기에 편함
    ArrayList<String> data;
    Context context;
    //생성자
    public CustomAdapter(Context context, ArrayList<String> data){
        this.context = context;
        this. data = data;
    }

    //밑에 함수는 data의 정보를 사용하게 됨, 사용자가 직접 호출하는 함수는 없고 다 시스템이 호출함

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) { //호출되는 목록 아이템의 위치
        return data.get(position);
    }

    //뷰의 id를 리턴해줌
    @Override
    public long getItemId(int position) {
        return position;
    }
    //아이템을 목록에 나타나는 아이템 하나하나를 그려줌
    @Override
    public View getView(int position, View view, final ViewGroup viewGroup) {
        //레이아웃 인플레이터는 xml 파일을 View 객체로 변환을 하게 됨
        //Context를 넘겨받는 것은 2가지가 있는데 첫번째는 생성자에 context를 생성하여 context.getApplicationContext()를 넘겨주는 방법
        //2. viewGroup.getContext()하는 방법이 있다. 3. view.getContext()도 있지만 null 값이 넘어얼 수 있으므로 쓰지 않는다.
        //View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null); //viewGroup이 지정되면 레이아웃을 viewGroup에 넣어주겠다는 뜻
        Holder holder = null;
        if(view==null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null); //itemview를 정의하는 것보다 좋은 것은 스크롤을 올릴 때 사라진 뷰를 재사용 할 수 있음
            view.setBackgroundColor(Color.CYAN);
            //아이템이 최초 호출될 경우는 Holder에 위젯을 담고
            holder = new Holder(view);

            // 홀더를 view에 붙여놓는다.
            view.setTag(holder); //라벨링 하기 위한 함수, 원래는 String 등을 넘겨서 라벨을 하려고 했는데 홀더를 넘겨서 홀더가 view를 가리키게 함


        }else{
            // View에 붙어 있는 홀더를 가져온다.
            holder = (Holder) view.getTag(); //가리키고 있는 홀더를 가져옴
        }
        //viewGroup.setBackgroundColor(Color.RED);

        // 뷰 안에 있는 텍스트뷰 위젯에 값을 입력한다. 내렸다 올렸다 하면서 메모리 많이 잡아 먹음, 그래서 Holder Pattern이라는 것이 있음
        // Holder에 담아서 그대로 사용하는 것

        holder.textView.setText(data.get(position));

        return view;
    }
}

class Holder{
    TextView textView;

    public Holder(View view){
        textView = view.findViewById(R.id.textView);
        setClickListener();
    }
    public void setClickListener(){
        textView.setOnClickListener(new View.OnClickListener() {
            //화면에 보여지는 뷰는 자신이 속한 component의 context를 그대로 가지고 있다.
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);//화면에 뿌려지는 모든 view는 상위 context를 가지고 있음
                intent.putExtra("valueKey", textView.getText().toString()); //인텐트에 값을 받아 다른 액티비티에 값을 전달해 줄 수 있다.
                view.getContext().startActivity(intent);
            }
        });
    }
}
