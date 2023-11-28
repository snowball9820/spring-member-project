package snowball.core.singleton;

public class StatefulService {
//    private int price; //상태를 유지하는 필드

    //주문을 해서 값을 저장하고
    public int order(String name, int price){
        System.out.println("name = " +name+"price = "+price);
//        this.price=price; //여기 문제
        return price;
    }
    //값을 꺼내고 싶었음
//    public int getPrice(){
////        return price;
//    }
}
