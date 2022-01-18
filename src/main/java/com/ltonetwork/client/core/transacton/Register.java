package com.ltonetwork.client.core.transacton;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;
import com.ltonetwork.client.exceptions.BadMethodCallException;
import com.ltonetwork.client.exceptions.InvalidArgumentException;
import com.ltonetwork.client.types.*;

import java.util.ArrayList;

public class Register extends Transaction {
    private final static long BASE_FEE = 100_000_000;
    private final static long VAR_FEE = 10_000_000;
    private final static byte TYPE = 20;
    private final static byte VERSION = 3;
    private final ArrayList<PublicKey> accounts;

    public Register() {
        super(TYPE, VERSION, BASE_FEE);
        accounts = new ArrayList<>();
    }

    public Register(JsonObject json) {
        super(json);

        JsonObject jsonAccounts = new JsonObject(json.get("accounts").toString(), true);
        accounts = new ArrayList<>();

        for (int i = 0; i < jsonAccounts.length(); i++) {
            JsonObject curr = new JsonObject(jsonAccounts.get(i), false);
            Key.KeyType keyType = Key.KeyType.valueOf(curr.get("keyType").toString());
            String key = curr.get("publicKey").toString();
            accounts.add(new PublicKey(key, Encoding.BASE58, keyType));
            this.fee += VAR_FEE;
        }
    }

    public byte[] toBinary() {
        if (this.senderPublicKey == null) {
            throw new BadMethodCallException("Sender public key not set");
        }

        if (this.timestamp == 0) {
            throw new BadMethodCallException("Timestamp not set");
        }

        byte[] ret = Bytes.concat(
                new byte[]{this.type},
                new byte[]{this.version},
                new byte[]{this.sender.getChainId()},
                Longs.toByteArray(this.timestamp),
                this.senderPublicKey.toRaw(),
                Longs.toByteArray(this.fee),
                Shorts.toByteArray((short) accounts.size())
        );

        for (PublicKey key : accounts) ret = Bytes.concat(ret, key.toBinary());

        return ret;
    }

    public void addAccount(PublicKey account) {
        accounts.add(account);
        this.fee += VAR_FEE;
    }
}
