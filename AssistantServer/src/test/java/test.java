public class test{
    //声明异常类型  在此方法中不需要立马抓取  声明以后会让 上一层抓取
    public void exTest() throws NullPointerException {
        //抛出异常   手动抛出
        throw new NullPointerException("这是一个异常");
    }

    public static void main(String[] args) {
        test t = new test();
        //抓取异常
        try {
            t.exTest();
            //显示异常
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}