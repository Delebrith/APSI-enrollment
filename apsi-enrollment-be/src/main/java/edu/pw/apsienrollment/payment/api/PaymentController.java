package edu.pw.apsienrollment.payment.api;

import edu.pw.apsienrollment.common.api.dto.SearchRequestDTO;
import edu.pw.apsienrollment.payment.api.dto.PaymentDto;
import edu.pw.apsienrollment.payment.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
@Validated
public class PaymentController {
    private final PaymentService paymentService;

    @ApiOperation(value = "Get list of user payments", nickname = "get list of user payments", notes="",
        authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping
    ResponseEntity<Iterable<PaymentDto>> getUserPayments(@Valid SearchRequestDTO request) {
        return ResponseEntity.ok(paymentService.findUserPayments(request.getPage(), request.getSize())
                .map(PaymentDto::of));
    }
}
