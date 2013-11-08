import javax.activation.MimetypesFileTypeMap;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 12:15 PM
 */
public class ContentTypeTest {
    public static void main(String[] args) {
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        String contentType = fileTypeMap.getContentType("badu.pdf");
        System.out.println(contentType);
    }
}
