package alerts;

import base.BaseTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class FileUploadTest extends BaseTests {

    @Test
    public void testFileUpload(){
        var uploadPage = homePage.clickFileUpload();
        uploadPage.uploadFile("C:\\Users\\jesus\\OneDrive\\Escritorio\\Proyectos\\SeleniumWebDriverWithJava\\resources\\1684449031601.jpg");
        assertEquals(uploadPage.getUploadedFiles(), "1684449031601.jpg");
    }

}
