package com.changgou.oauth;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import java.io.IOException;

public class TestParserJwtPermissions {
    @Test
    public void test01() throws IOException {
        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU3NDM4NDk1MCwiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiZ29vZHNfbGlzdCJdLCJqdGkiOiIxNTJhODBkMi05MzRjLTRkYzctYjQzOS05YWNiMjVhNmVmYTYiLCJjbGllbnRfaWQiOiJjaGFuZ2dvdSIsInVzZXJuYW1lIjoiamFja21hIn0.d3GYztvImzRZp1JQMmvRp6Ag2OdsoWm6JzbJTsqJpjWgeFXBfE7233gdi55jBTTPULJ86p4LqLZduDUPnGh5W8o9rb5g04QPpTVY7guFWTO-26IdG5yg4AgPfcmQtVMAtI543OrGTTSMOEBSi-p5a7ch_z_ECp8P8ftM_ndG_WH1__ZiPxcPz3HytuibfVGLULgGbQl6szgJYwddlxLqOhl7mmacA8ze-btEOWDwQnHhEKpwfxrMpFPw7af6wUPUxx_ArbdzwnxFwDhho9z6ONnIx6-j7UZb92GL3WLaV5yi4Fm9pniVB8brpBWg_h2qPTDJ614PkNflEAx484pcXQ";
        String publicKey = IOUtils.toString(new ClassPathResource("public.key").getInputStream());
        Jwt verify = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));
        System.out.println(verify.getClaims());
    }
    @Test
    public void test02() throws IOException {
        String publicKey = IOUtils.toString(new ClassPathResource("public.key").getInputStream());
        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU3NDM4OTA0MCwiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiUk9MRV9BRE1JTiIsImdvb2RzX2xpc3QiLCJST0xFX1VTRVIiXSwianRpIjoiMWE5YzUyNzktNGRmNy00OTc1LTg2Y2ItZWU4MzI3OTAyOWQxIiwiY2xpZW50X2lkIjoiY2hhbmdnb3UiLCJ1c2VybmFtZSI6ImphY2ttYSJ9.lzlrONKQEhjWA1LUeJgMj45P5jPY37lGp0ifHbh3m7EI99pMctcvq6uRN_03riQyGBSJGzL4bQJAz7r6JOatulPBgu0reQ7LNZQFh6ieQSz-XZD4xYTlRbCROI0RUR9gG3Cv34btjSEoCEiw2X19GovvgB4tOBFD7FGET8X2pUrr4xHBGJncpKEsCHOkGeTQsJ_yif9LhBieUur5FRcy5BaMv1SHYdiPCymyXIGsbUk-cssJsJ0d5QQxyg-Dml510pKY2UpT9hhBm4krdJnfkP2lgYKQjn1mqfQ-upKNCt2wcmz1BYoQHrNP4h5FvvfCsJIxRhLQomIsO0LZXr9uOw";
        Jwt verify = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));
        System.out.println(verify.getClaims());
    }

}
