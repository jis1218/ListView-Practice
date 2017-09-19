# ArrayList와 BaseAdapter를 이용하여 ListView 구성하기

##### ArrayList에 필요한 데이터를 넣고 BaseAdapter를 이용하여 ListView와 ArrayList의 데이터를 연결시켜준다.
##### 그 과정은 다음과 같다.
```java
CustomAdapter adapter = new CustomAdapter(this, data);
listView.setAdapter(adapter);
class CustomAdapter extends BaseAdapter{
  ...
  ...
  public View getView(int position, View view, final ViewGroup viewGroup) {
    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
  }
}
```
##### 코드에서와 같이 getView의 파라미터인 view에 사용하고자 하는 layout을 넣어준다. 그 방법은 LayoutInflater로 하면 된다.
##### LayoutInflater에 들어갈 인자 중 Context의 경우 넘겨주는 방법이 3가지가 있는데
##### 1. 생성자에 context를 생성하여 context를 넘겨주는 방법, 2. 파라미터 중 하나인 viewGroup에 있는 getContext()를 이용하는 방법 3. view에 있는 getContext()를 이용하는 방법이 있다. 하지만 view.getContext()는 view가 null일 수 있으므로 되도록 사용하지 않는다.


### 새로 배운 내용

#### Adapter의 최적화 방법 3가지(이것이 안드로이드다 책에 나와 있는 내용 추가)
##### 1. Layout 최적화
##### 리스트에 들어가는 레이아웃의 수를 최대한 줄이면 된다. LinearLayout으로 여러개 쓰기보단 Constraint나 Relative를 이용하여 나타내면 하나에 layout에 다 나타낼 수 있다.

##### 2. View 재사용하기
##### getView 함수가 호출될 때마다 inflate 함수가 호출되고 view 객체가 늘어나게 되는데 이는 메모리 부족을 일으키므로 view를 재사용할 수 있어야 한다. 이를 다음과 같이 해결한다. (getView함수는 Convert View(기존의 아이템을 변형하여 사용하는 view)를 제공하는 것 같다.)
```java
public View getView(int position, View view, final ViewGroup viewGroup) {
if(view==null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
}
...
}
```

##### 3. View Holder 사용하기
##### view를 만들어줄 때 view안에 다른 자식 view의 객체도 역시 생성을 해줘야 하는데 view의 findByView 함수가 호출될 때마다 성능에 영향이 있으므로 view 객체를 처음 만들어줄 때만 자식 view를 참조하도록 한다. 그렇게 하기 위해 View Holder를 만들어 주는데
```java
class Holder{
    TextView textView;

    public Holder(View view){
        textView = view.findViewById(R.id.textView);
      }
    }
```
##### 그리고 setTag, getTag를 이용해 객체를 넘겨준다.
```java
if(view==null) {
  view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
  holder = new Holder(view);
  view.setTag(holder);
}else{
  holder = (Holder) view.getTag();
  holder.textView.setText(data.get(position));
}
```
