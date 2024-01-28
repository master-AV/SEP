package ftn.sep.bitcoin.service;

import com.hedera.hashgraph.sdk.*;
import ftn.sep.dto.BPaymentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static ftn.sep.bitcoin.util.Constants.HEDERA_RSD_EXCHANGE_RATE;

@Service
public class PaymentService {

    @Value("${succ.url}")
    private String succUrl;

    @Value("${err.url}")
    private String errUrl;

    @Value("${fail.url}")
    private String failUrl;

    public void payWithHedera(BPaymentDTO paymentDTO) throws PrecheckStatusException, TimeoutException, ReceiptStatusException, NotFoundException {

        if (paymentDTO.getReceivingAccountID() == null)
            throw new NotFoundException();

        paymentDTO.setPrice(paymentDTO.getPrice() * HEDERA_RSD_EXCHANGE_RATE);

        //Grab your Hedera testnet account ID and private key
        AccountId sendingAccountId = AccountId.fromString(paymentDTO.getSendingAccountID());//Dotenv.load().get("MY_ACCOUNT_ID")
        PrivateKey sendingPrivateKey = PrivateKey.fromString(paymentDTO.getSendingAccountKey());//Dotenv.load().get("MY_PRIVATE_KEY")

        PrivateKey recievingAccountKey = PrivateKey.fromString(paymentDTO.getReceivingAccountKey());
        AccountId recievingAccountId = AccountId.fromString(paymentDTO.getReceivingAccountID());

        Client client = Client.forTestnet();
        client.setOperator(sendingAccountId, sendingPrivateKey); // operator je taj koji sifruje - koji salje novac
        /* treba mi clijent account id i privatni kljuc*/

        // Set default max transaction fee & max query payment
//        client.setMaxTransactionFee(new Hbar(100));
//        client.setMaxQueryPayment(new Hbar(30));

        System.out.println("\nReceiving account ID: " +recievingAccountId); // acount id

        //Check the new account's balance
        AccountBalance accountBalance = new AccountBalanceQuery()
                .setAccountId(recievingAccountId)
                .execute(client);

        System.out.println("Receiving account balance: " +accountBalance.hbars);

        AccountBalance myAccountBalance = new AccountBalanceQuery()
                .setAccountId(sendingAccountId)
                .execute(client);

        System.out.println("Sending account balance: " +myAccountBalance.hbars);

        //Transfer HBAR
        TransactionResponse sendHbar = new TransferTransaction()
                .addHbarTransfer(sendingAccountId, Hbar.fromTinybars((long) (paymentDTO.getPrice()*-1)))
                .addHbarTransfer(recievingAccountId, Hbar.fromTinybars((long) paymentDTO.getPrice()))
                .execute(client); // potpise

        //sendHbar.getReceipt(client);
        System.out.println("The transfer transaction from send account to the rec account was: " + sendHbar.getReceipt(client));
        //Check the new account's balance
        AccountBalance accountBalance2 = new AccountBalanceQuery()
                .setAccountId(recievingAccountId)
                .execute(client);

        System.out.println("Recieving account balance: " +accountBalance2.hbars);

        AccountBalance myAccountBalance2 = new AccountBalanceQuery()
                .setAccountId(sendingAccountId)
                .execute(client);

        System.out.println("Sending balance: " +myAccountBalance2.hbars);
    }

    public Map<String, String>  pay(BPaymentDTO paymentDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            payWithHedera(paymentDTO);
            response.put("status", "success");
            response.put("redirectUrl", succUrl);
        }catch (NotFoundException notFoundException){
            response.put("status", "fail");
            response.put("redirectUrl", failUrl);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("redirectUrl", errUrl);
        }
        return response;
    }

}
