package com.ayuda.referral.config;

// import java.io.IOException;
// import java.io.Reader;

import com.ayuda.referral.server.Error;
// import com.ayuda.referral.server.ServiceResponse;
// import com.fasterxml.jackson.databind.DeserializationFeature;
// import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorConfig implements ErrorDecoder {

  private final ErrorDecoder decoder = new Default();

  @Override
  public Exception decode(String s, Response res) {
    // String message = null;
    // Reader reader = null;

    // try {
    //   reader  = res.body().asReader();
    //   String result = "";
    //   while (reader.read() != -1) {
    //     result += (char) reader.read();
    //   }
    //   ObjectMapper mapper = new ObjectMapper();
    //   mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    //   @SuppressWarnings("unchecked")
    //   ServiceResponse<Object> r = mapper.readValue(result, ServiceResponse.class);
    //   message = (String) r.getResponse();
    //   reader.close();
    // } catch (IOException e) {
    //   e.printStackTrace();
    // }

    switch(res.status()) {
      case 400:
        throw new Error(400, "Service responded with a 400.");
      case 401:
        throw new Error(401, "Service responded with a 401.");
      case 402:
        throw new Error(402, "Service responded with a 402.");
      case 500:
        throw new Error(500, "An internal server error occured");
      // default:
      //   throw new Error(500, "An error occured");
    }
    return decoder.decode(s, res);
  }
  
}