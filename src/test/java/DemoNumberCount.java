/**
 * Programmer : pancara
 * Date       : 11/18/12
 * Time       : 2:50 PM
 */
public class DemoNumberCount {
    public static void main(String[] args) {

        int count_num = 0;

        for (int i = 1; i <= 1000; i++) {
            if ((i % 4) == 0) {
                if ((i % 10) > 0) {
                    if ((i % 25) > 0) {
                        count_num++;
                        System.out.println(i);
                    }
                }
            }
        }

        System.out.println(count_num);
    }
}
