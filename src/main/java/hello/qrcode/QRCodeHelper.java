package hello.qrcode;
import org.springframework.web.client.RestTemplate;
import org.hibernate.loader.custom.Return;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "qrcode")
public class QRCodeHelper implements IQRCodeHelper
{

    private String qrcodeurl;

    /**
     * @return the qrcodeurl
     */
    public String getQrcodeurl() {
        return qrcodeurl;
    }

    /**
     * @param qrcodeurl the qrcodeurl to set
     */
    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl;
    }


    public String GetQrcodeByValue(String value){

        String requestUrl =qrcodeurl+"/"+value;

        System.out.println("****************************************************************************************************************************");
        System.out.println(requestUrl);
        System.out.println("****************************************************************************************************************************");


        RestTemplate restTemplate = new RestTemplate();
        String url =restTemplate.getForObject(requestUrl, String.class);

        return url;
    }


}
