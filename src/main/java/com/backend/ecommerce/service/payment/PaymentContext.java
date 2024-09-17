//package com.backend.ecommerce.service.payment;
//
//
//import com.backend.ecommerce.enums.TransactionType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PaymentContext {
//
//    private final BankPaymentStrategy bankPaymentStrategy;
//    private final CreditCardPaymentStrategy creditCardPaymentStrategy;
//    private final DebitCardPaymentStrategy debitCardPaymentStrategy;
//    private final UPIPaymentStrategy upiPaymentStrategy;
//    private final IMPSPaymentStrategy impsPaymentStrategy;
//
//    @Autowired
//    public PaymentContext(BankPaymentStrategy bankPaymentStrategy,
//                          CreditCardPaymentStrategy creditCardPaymentStrategy,
//                          DebitCardPaymentStrategy debitCardPaymentStrategy,
//                          UPIPaymentStrategy upiPaymentStrategy,
//                          IMPSPaymentStrategy impsPaymentStrategy) {
//        this.bankPaymentStrategy = bankPaymentStrategy;
//        this.creditCardPaymentStrategy = creditCardPaymentStrategy;
//        this.debitCardPaymentStrategy = debitCardPaymentStrategy;
//        this.upiPaymentStrategy = upiPaymentStrategy;
//        this.impsPaymentStrategy = impsPaymentStrategy;
//    }
//
//    public PaymentStrategy getPaymentStrategy(TransactionType transactionType) {
//        return switch (transactionType) {
//            case BANK -> bankPaymentStrategy;
//            case CREDIT_CARD -> creditCardPaymentStrategy;
//            case DEBIT_CARD -> debitCardPaymentStrategy;
//            case UPI -> upiPaymentStrategy;
//            case IMPS -> impsPaymentStrategy;
//            default -> throw new IllegalArgumentException("Unsupported transaction type");
//        };
//    }
//}
