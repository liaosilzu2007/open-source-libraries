package com.lzumetal.open.source.zxing;



import com.lzumetal.open.source.zxing.util.ZxingUtil;
import org.junit.Test;

import java.io.File;

public class MainTest {


    @Test
    public void testQrcode() {
        File file = new File("f:/bigtopicposter-big-qr.png");
        ZxingUtil.encodeQrcodeForFile("http://localhost:8090/shop/getOrder?id=100", file);
    }


    @Test
    public void testBarcode() {
        File file = new File("d:/abc.png");
        ZxingUtil.encodeBarcodeForFile("http://localhost:8090/shop/getOrder?id=100",null, file);
    }

}
