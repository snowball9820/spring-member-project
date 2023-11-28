package snowball.core.singleton;

public class SingletonService {
    //관례상 private로 하고 static으로 가지면 클래스 레벨에 올라가기 때문에 딱 하나만 존재하게 됨
    //1.static영역에 객체를 딱 1개만 생성
    private static final SingletonService instance = new SingletonService(); //객체를 생성하고 자기자신을 생성해서 instance에 참조를 넣어둠

    //2.public으로 열고 객체 인스턴스가 필요하면 이 static 메서드를 통해 조회하도록 허용
    //조회할 때 사용
    public static SingletonService getInstance(){
        return instance;
    }

   //private으로 만들어서 외부에서는 사용하지 못하게 함
    //3.생성자를 private로 선언, 외부에서 new 키워드를 사용한 객체 생성 막음
    private SingletonService(){
    }
    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }


}
