package mk.trkalo.emtlab.EMTlab.config;

import mk.trkalo.emtlab.EMTlab.model.payloads.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

//@ControllerAdvice
//public class ExceptionHandlerClass {
//
//    @ExceptionHandler(value ={ Exception.class})
//    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
//        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}