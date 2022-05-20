package com.ltonetwork.client.core.transaction;

import com.ltonetwork.client.TestUtil;
import com.ltonetwork.client.core.Account;
import com.ltonetwork.client.exceptions.BadMethodCallException;
import com.ltonetwork.client.types.JsonObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;


public class CancelLeaseTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    CancelLease tx;

    @Before
    public void init() {
        tx = new CancelLease("3MsE8Jfjkh2zaZ1LCGqaDzB5nAYw5FXh");
    }

    @Test
    public void testToBinaryV3() {
        Account account = TestUtil.createAccount();
        tx.signWith(account);

        assertEquals(84, tx.toBinary().length);
    }

    @Test
    public void testToBinaryV2() {
        Account account = TestUtil.createAccount();
        CancelLease txV2 = new CancelLease("3MsE8Jfjkh2zaZ1LCGqaDzB5nAYw5FXh", (byte) 2);
        txV2.signWith(account);

        assertEquals(83, txV2.toBinary().length);
    }

    @Test
    public void testToBinaryFail() {
        expectedEx.expect(BadMethodCallException.class);
        expectedEx.expectMessage("Sender public key not set");

        tx.toBinary();
    }

    @Test
    public void testCreateWithJson() {
        JsonObject json = new JsonObject(
                "{\n" +
                        "  \"type\": 9,\n" +
                        "  \"id\": \"oYv8LBTsLRyAq1w7n9UXudAf5Luu9CuRXkYSnxLX2oa\",\n" +
                        "  \"sender\": \"3N51gbw5W3xvSkcAXtLnXc3SQh2m9e6TBcy\",\n" +
                        "  \"senderKeyType\": \"ed25519\",\n" +
                        "  \"senderPublicKey\": \"8wFR3b8WnbFaxQEdRnogTqC5doYUrotm3P7upvxPaWUo\",\n" +
                        "  \"fee\": 100000,\n" +
                        "  \"timestamp\": 1538728794530,\n" +
                        "  \"proofs\": [\"65E82MLn6RdF7Y2VrdtFWkHd97teqLSwVdbGyEfy7x6aczkHRDZMvNUfdTAYgqDXzDDKKEkQqVhMVMg6EEEvE3C3\"],\n" +
                        "  \"version\": 2,\n" +
                        "  \"leaseId\": \"3MsE8Jfjkh2zaZ1LCGqaDzB5nAYw5FXh\",\n" +
                        "  \"height\": 22654\n" +
                        "}", false);

        CancelLease jsonTx = new CancelLease(json);
        assertEquals(83, jsonTx.toBinary().length);
    }
}
