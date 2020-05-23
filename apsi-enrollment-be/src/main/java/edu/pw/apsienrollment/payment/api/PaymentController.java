package edu.pw.apsienrollment.payment.api;

import edu.pw.apsienrollment.common.api.dto.SearchRequestDTO;
import edu.pw.apsienrollment.payment.api.dto.CurrencyDto;
import edu.pw.apsienrollment.payment.api.dto.PaymentCreateDto;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @ApiOperation(value = "Get currency", nickname = "get currency", notes="",
        authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If everything went ok", response = CurrencyDto.class)})
    @GetMapping("currency")
    ResponseEntity<CurrencyDto> getCurrency() {
        return ResponseEntity.ok(new CurrencyDto(paymentService.getCurrency()));
    }

    @ApiOperation(value = "Create payment", nickname = "create payment", notes="",
        authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided"),
            @ApiResponse(code = 404, message = "If specified enrollment does not exist")})
    @PostMapping
    ResponseEntity<PaymentDto> createPayment(@Valid @RequestBody PaymentCreateDto request, HttpServletRequest servletRequest) {
        return ResponseEntity.ok(PaymentDto.of(paymentService.createPayment(
            request.getEnrollmentId(), servletRequest.getRemoteAddr()
        )));
    }
}
